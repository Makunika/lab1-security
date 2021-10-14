package security.gamma.enigma;

import security.crypto.enigma.EnigmaKey;
import security.gamma.base.GammaGenerator;
import security.random.enigma.EnigmaRandomGenerator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maxim Pshiblo
 */
public class EnigmaGammaGenerator implements GammaGenerator {

    private final EnigmaRandomGenerator randomGenerator;

    public EnigmaGammaGenerator(EnigmaKey key) {
        randomGenerator = new EnigmaRandomGenerator(key);
    }

    @Override
    public String gamma(String origin) {
        List<Character> randomChars = randomGenerator.nextChars(origin.length());
        System.out.println(randomChars.stream().map(ch -> String.valueOf((int) ch)).collect(Collectors.joining(", ")));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            char charValue = origin.charAt(i);
            char result = (char) (charValue ^ randomChars.get(i));
            sb.append(result);
        }
        String result = sb.toString();
        System.out.println(result);
        return result;
    }
}
