package security.swap;

import security.base.Alphabet;

import java.util.LinkedHashSet;

public abstract class SwapAlphabet implements Alphabet {

    protected LinkedHashSet<Character> alphabet;

    public SwapAlphabet() {
        alphabet = new LinkedHashSet<>();
        fillAlphabet();
    }

    protected abstract void fillAlphabet();

    @Override
    public boolean isValid(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!alphabet.contains(str.charAt(i))) {
                return false;
            }
        }
        return isMoreValid(str);
    }

    @Override
    public LinkedHashSet<Character> getAlphabet() {
        return alphabet;
    }

    protected boolean isMoreValid(String str) {
        return true;
    }
}
