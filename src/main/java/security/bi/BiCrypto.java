package security.bi;

import lombok.Getter;
import lombok.Setter;
import security.base.Crypto;
import security.exceptions.CryptoException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BiCrypto implements Crypto<BiKey> {

    private BiKey biKey;
    private BiTable table;

    public BiCrypto(BiKey biKey) {
        this.biKey = biKey;
        table = new BiTable(biKey);
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        if (!table.isInit()) {
            table.init();
        }
        if (biKey == null) {
            throw new CryptoException("key is null");
        }
        if (origin == null) {
            throw new CryptoException("origin is null");
        }
        String lowerCase = origin.toUpperCase();
        if (!BiAlphabet.getInstance().isValid(lowerCase)) {
            throw new CryptoException("origin not valid");
        }

        List<BiGrammarly> grammarlies = toGrammarly(lowerCase);
        List<BiGrammarly> resultGrammarly = new ArrayList<>();

        for (BiGrammarly grammarly : grammarlies) {
            resultGrammarly.add(table.encrypt(grammarly));
        }

        return toString(resultGrammarly);
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        if (!table.isInit()) {
            table.init();
        }
        if (biKey == null) {
            throw new CryptoException("key is null");
        }
        if (crypto == null) {
            throw new CryptoException("origin is null");
        }
        if (crypto.length() % 2 != 0) {
            throw new CryptoException("crypto.length() % 2 != 0");
        }

        List<BiGrammarly> grammarlies = new ArrayList<>();
        for (int i = 0; i < crypto.length(); i+=2) {
            BiGrammarly grammarly = new BiGrammarly();
            grammarly.setCharacters(crypto.charAt(i), crypto.charAt(i + 1));
            grammarlies.add(grammarly);
        }

        List<BiGrammarly> resultGrammarly = new ArrayList<>();

        for (BiGrammarly grammarly : grammarlies) {
            resultGrammarly.add(table.decrypt(grammarly));
        }

        String result = toString(resultGrammarly);
        return result.trim().replaceAll(" {2,}", " ");
    }

    private List<BiGrammarly> toGrammarly(String str) {
        StringBuilder sb = new StringBuilder(str.trim());
        List<BiGrammarly> result = new ArrayList<>();
        int lastIdLetter = 0;
        boolean check;
        String tmp = str.trim();
        do {
            check = false;
            for (int i = 0; i < tmp.length(); i+=2) {

                char char1 = tmp.charAt(i);
                char char2 = i + 1 != tmp.length() ? tmp.charAt(i + 1) : char1 != '.' ? '.' : ' ';

                if (char1 == ' ' && char2 != ' ') {
                    lastIdLetter = i;
                } else if (char2 == ' ' && char1 != ' ') {
                    lastIdLetter = i + 1;
                }

                if (char1 == char2) {
                    if (char1 == ' ') {
                        sb.replace(i, i + 1, "");
                    }
                    sb.insert(lastIdLetter, ' ');
                    check = true;
                    break;
                }
            }
            tmp = sb.toString();
        } while (check);

        if (sb.length() % 2 != 0) {
            sb.append(' ');
        }

        for (int i = 0; i < sb.length(); i+=2) {
            BiGrammarly grammarly = new BiGrammarly();
            grammarly.setCharacters(sb.charAt(i), sb.charAt(i + 1));
            result.add(grammarly);
        }

        return result;
    }

    private String toString(List<BiGrammarly> grammarlies) {
        StringBuilder sb = new StringBuilder();
        for (BiGrammarly grammarly : grammarlies) {
            sb.append(grammarly.getCharacterOne());
            sb.append(grammarly.getCharacterTwo());
        }
        return sb.toString();
    }

    @Override
    public void setKey(BiKey key) {
        table.setKey(key);
        this.biKey = key;
    }

    @Override
    public BiKey getKey() {
        return biKey;
    }
}
