package security.bi;

import security.base.Alphabet;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
        return true;
    }

    @Override
    public LinkedHashSet<Character> getAlphabet() {
        return alphabet;
    }
}
