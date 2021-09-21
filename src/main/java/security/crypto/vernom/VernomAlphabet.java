package security.crypto.vernom;

import security.crypto.base.Alphabet;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class VernomAlphabet implements Alphabet {

    private static final VernomAlphabet instance = new VernomAlphabet();

    public static VernomAlphabet getInstance() {
        return instance;
    }

    private final LinkedHashSet<Character> alphabet;
    private final HashMap<Character, byte[]> bitsMap;
    private final HashMap<String, Character> bitsMapString;

    private VernomAlphabet() {
        alphabet = new LinkedHashSet<>();
        bitsMap = new HashMap<>();
        bitsMapString = new HashMap<>();
        fillAlphabet();
    }

    private void fillAlphabet() {
        for (int i = '0'; i <= (int)'9'; i++) {
            alphabet.add((char) i);
        }
        for (int i = 'а'; i <= (int)'я'; i++) {
            if (i == (int)'ж') {
                alphabet.add('ё');
            }
            alphabet.add((char) i);
        }
        alphabet.add(' ');
        alphabet.add('.');
        alphabet.add(',');
        alphabet.add('!');
        alphabet.add('?');
        System.out.println("size alphabet = " + alphabet.size());

        int n = 0;
        for (Character character : alphabet) {
            String bitsStr = Integer.toBinaryString(n);
            if (bitsStr.length() < 6) {
                bitsStr = "0".repeat(6 - bitsStr.length()) + bitsStr;
            }
            bitsMap.put(character, VernomUtils.parseStringToBits(bitsStr));
            bitsMapString.put(bitsStr, character);
            n++;
        }
        System.out.println("size bits = " + bitsMap.size());
    }

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!alphabet.contains(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public LinkedHashSet<Character> getAlphabet() {
        return alphabet;
    }

    public HashMap<Character, byte[]> getBitsMap() {
        return bitsMap;
    }

    public HashMap<String, Character> getBitsMapString() {
        return bitsMapString;
    }
}
