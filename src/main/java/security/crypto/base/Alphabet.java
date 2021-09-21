package security.crypto.base;

import java.util.LinkedHashSet;

public interface Alphabet {
    boolean isValid(String str);
    LinkedHashSet<Character> getAlphabet();
}
