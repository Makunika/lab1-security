package security.bi;

import lombok.Getter;
import lombok.Setter;
import security.base.Encryption;
import security.exceptions.EncryptionException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BiEncryption implements Encryption<BiKey> {

    private BiKey biKey;
    private BiTable table;

    public BiEncryption(BiKey biKey) {
        this.biKey = biKey;
        table = new BiTable(biKey);
    }

    @Override
    public String encryption(String origin) throws EncryptionException {
        if (!table.isInit()) {
            table.init();
        }
        if (biKey == null) {
            throw new EncryptionException("key is null");
        }
        if (origin == null) {
            throw new EncryptionException("origin is null");
        }
        String lowerCase = origin.toLowerCase();
        if (!BiAlphabet.getInstance().isValid(lowerCase)) {
            throw new EncryptionException("origin not valid");
        }

        List<BiGrammarly> grammarlies = toGrammarly(lowerCase);
        List<BiGrammarly> resultGrammarly = new ArrayList<>();

        for (BiGrammarly grammarly : grammarlies) {
            resultGrammarly.add(table.encrypt(grammarly));
        }

        return toString(resultGrammarly);
    }

    private List<BiGrammarly> toGrammarly(String str) {
        StringBuilder sb = new StringBuilder(str.trim());
        List<BiGrammarly> result = new ArrayList<>();
        int lastIdLetter = 0;
        String tmp = str.trim();
        for (int i = 0; i < tmp.length(); i+=2) {

            char char1 = tmp.charAt(i);
            char char2 = tmp.charAt(i + 1);

            if (char1 == ' ' && char2 != ' ') {
                lastIdLetter = i;
            } else if (char2 == ' ' && char1 != ' ') {
                lastIdLetter = i + 1;
            }

            if (char1 == char2) {
                sb.insert(lastIdLetter, ' ');
            }
        }

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
