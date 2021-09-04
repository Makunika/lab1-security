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
        for (int i = 'а'; i <= (int)'я'; i++) {
            alphabet.add((char) i);
        }
        alphabet.add(' ');
        alphabet.add('.');
        alphabet.add(',');
    }

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!alphabet.contains(str.charAt(i))) {
                return false;
            }
        }
        return !Pattern.matches("([а-я])\\1{2,}", str);
    }

    @Override
    public LinkedHashSet<Character> getAlphabet() {
        return alphabet;
    }
}
