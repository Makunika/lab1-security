package security.base;

import java.util.LinkedHashSet;
import java.util.Set;

public interface Alphabet {
    boolean isValid(String str);
    LinkedHashSet<Character> getAlphabet();
}
