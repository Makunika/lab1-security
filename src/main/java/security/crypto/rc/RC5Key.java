package security.crypto.rc;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Maxim Pshiblo
 */
@Getter
public class RC5Key {

    private final String key;
    private final int w;
    private final int w4;
    private final int w8;
    private final int mod;

    public RC5Key(String key) {
        this.key = key;
        w = 32;
        w4 = w / 4;
        w8 = w / 8;
        mod = (int) Math.pow(2, w);
    }

    @Override
    public String toString() {
        return "key (ключ) = '" + key + "'\n" +
                "w (размер одного слова в битах) = " + w + "\n" +
                "w4 (размер в байтах 2 слова) = " + w4 + "\n" +
                "w8 (размер в байтах 1 слова) = " + w8 + "\n" +
                "mod (2 в степени w) = " + mod + "\n";
    }
}
