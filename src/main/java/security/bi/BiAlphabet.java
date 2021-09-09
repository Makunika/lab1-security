package security.bi;

import security.base.Alphabet;

import java.util.LinkedHashSet;
import java.util.regex.Pattern;

public class BiAlphabet implements Alphabet {

    private static final BiAlphabet instance = new BiAlphabet();

    public static BiAlphabet getInstance() {
        return instance;
    }

    private final LinkedHashSet<Character> alphabet;

    private BiAlphabet() {
        alphabet = new LinkedHashSet<>();
        fillAlphabet();
    }

    private void fillAlphabet() {
        for (int i = 'А'; i <= (int)'Я'; i++) {
            alphabet.add((char) i);
        }
        alphabet.add(' ');
        alphabet.add('.');
        alphabet.add('Ё');
    }

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!alphabet.contains(str.charAt(i))) {
                return false;
            }
        }
        return isLetterStretchMoreTwo(str);
    }

    private boolean isLetterStretchMoreTwo(String str) {
        char current = str.charAt(0);
        int count = 0;
        for (int i = 1; i < str.length(); i++) {
            if (current == str.charAt(i)) {
                count++;
            } else {
                count = 0;
                current = str.charAt(i);
            }
            if (count > 2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public LinkedHashSet<Character> getAlphabet() {
        return alphabet;
    }
}
