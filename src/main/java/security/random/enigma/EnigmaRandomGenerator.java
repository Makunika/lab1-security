package security.random.enigma;

import lombok.SneakyThrows;
import security.crypto.enigma.EnigmaCrypto;
import security.crypto.enigma.EnigmaKey;
import security.crypto.enigma.rotor.EnigmaAlphabet;
import security.random.base.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Pshiblo
 */
public class EnigmaRandomGenerator implements RandomGenerator {

    private final EnigmaCrypto enigmaCrypto;
    private final List<Character> alphabet;
    private int counter;

    public EnigmaRandomGenerator(EnigmaKey key) {
        enigmaCrypto = new EnigmaCrypto(key);
        alphabet = new ArrayList<>(EnigmaAlphabet.getInstance().getAlphabet());
        counter = 0;
    }

    @SneakyThrows
    @Override
    public char nextChar() {
        char result = enigmaCrypto.encryption(String.valueOf(alphabet.get(counter))).charAt(0);
        counter++;
        if (counter > 34) {
            counter = 0;
        }
        return result;
    }

    @Override
    public List<Character> nextChars(int length) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(nextChar());
        }
        return result;
    }

    @SneakyThrows
    @Override
    public int nextInt() {
        return nextChar();
    }

    @Override
    public List<Integer> nextIntegers(int length) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(nextInt());
        }
        return result;
    }
}
