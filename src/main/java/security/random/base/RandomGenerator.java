package security.random.base;

import java.util.List;

/**
 * @author Maxim Pshiblo
 */
public interface RandomGenerator {
    char nextChar();
    List<Character> nextChars(int length);
    int nextInt();
    List<Integer> nextIntegers(int length);
}
