package security.crypto.enigma.rotor;

import security.crypto.swap.bi.BiAlphabet;

public class EnigmaAlphabet extends BiAlphabet {
    private static final EnigmaAlphabet instance = new EnigmaAlphabet();

    public static EnigmaAlphabet getInstance() {
        return instance;
    }

    @Override
    public boolean isMoreValid(String str) {
        return true;
    }
}
