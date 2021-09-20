package security.bi;

import security.swap.SwapAlphabet;

public class BiAlphabet extends SwapAlphabet {

    private static final BiAlphabet instance = new BiAlphabet();

    public static BiAlphabet getInstance() {
        return instance;
    }

    @Override
    protected void fillAlphabet() {
        for (int i = 'А'; i <= (int)'Я'; i++) {
            if (i == (int) 'Е') {
                alphabet.add('Ё');
            }
            alphabet.add((char) i);
        }
        alphabet.add(' ');
        alphabet.add('.');
    }

    @Override
    public boolean isMoreValid(String str) {
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
}
