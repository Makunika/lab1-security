package security.cracker.tremus;

import security.cracker.CrackInfo;
import security.cracker.CrackResult;
import security.cracker.base.AbstractCrack;
import security.cracker.base.KnownInfo;
import security.crypto.swap.tremus.TremosCrypto;
import security.crypto.swap.tremus.TremosKey;
import security.exceptions.CrackException;
import security.exceptions.CryptoException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Maxim Pshiblo
 */
public class TremusCracker extends AbstractCrack<TremosCrypto> {

    public TremusCracker(TremosCrypto crypto) {
        super(crypto);
    }

    @Override
    public CrackResult<TremosKey> crack(String msg, KnownInfo knownInfo) throws CrackException {
        if (knownInfo instanceof TremusKnownInfo) {
            TremusKnownInfo tremusKnownInfo = (TremusKnownInfo) knownInfo;
            CrackResult<TremosKey> result = new CrackResult<>();
            TremosKey key = new TremosKey();
            key.setRowCount(tremusKnownInfo.getRowCount());
            key.setLetter(tremusKnownInfo.getLetter());
            CrackInfo info = CrackInfo.getInstance();
            double currentDelta = 100000;
            int currentColumnCount = -1;
            int currentRowCount = -1;
            for (int column = 1; column < 100; column++) {
                for (int row = 1; row < 100; row++) {
                    try {
                        key.setColumnCount(column);
                        key.setRowCount(row);
                        crypto.setKey(key);
                        String decryption = crypto.decryption(msg).toLowerCase();
                        int length = decryption.length();
                        System.out.println(decryption);
                        HashMap<Character, Integer> countCharacters = new HashMap<>();
                        for (int j = 0; j < decryption.length(); j++) {
                            countCharacters.put(decryption.charAt(j), countCharacters.getOrDefault(decryption.charAt(j), 0) + 1);
                        }

                        double delta = 0.0f;
                        Set<Map.Entry<Character, Double>> entries = info.getChanceLowerCaseMap().entrySet();
                        for (Map.Entry<Character, Double> entry : entries) {
                            char character = entry.getKey();
                            double chance = entry.getValue();
                            double real = (double) countCharacters.getOrDefault(character, 0) / (double) length;
                            double error = real - chance;
                            delta += error * error;
                        }
                        System.out.println(delta);
                        if (delta < currentDelta) {
                            currentDelta = delta;
                            currentColumnCount = column;
                            currentRowCount = row;
                        }

                    } catch (CryptoException ignored) {}
                }
            }

            key.setColumnCount(currentColumnCount);
            key.setRowCount(currentRowCount);
            result.setKey(key);
            crypto.setKey(key);
            System.out.println("\n\n\nResult:");
            try {
                result.setEncrypt(crypto.decryption(msg));
            } catch (CryptoException e) {
                e.printStackTrace();
            }
            System.out.println(currentDelta);
            return result;
        } else {
            throw new CrackException("knownInfo not instanceof TremusKnownInfo");
        }
    }
}
