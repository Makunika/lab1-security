package security.crypto.rc;

import security.crypto.base.Crypto;
import security.exceptions.CryptoException;

/**
 * @author Maxim Pshiblo
 */
public class RC5Crypto implements Crypto<RC5Key> {

    private RC5Key key;
    private final RC5Block rc5Block;

    public RC5Crypto(RC5Key key) {
        this.key = key;
        rc5Block = new RC5Block(key);
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        if (!rc5Block.isInit()) {
            rc5Block.init();
        }

        int size = key.getW4();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < origin.length(); i+=size) {
            result.append(rc5Block.encrypt(origin.substring(i, Math.min(i + size, origin.length()))));
        }

        return result.toString();
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setKey(RC5Key key) {
        this.key = key;
        rc5Block.setKey(key);
    }

    @Override
    public RC5Key getKey() {
        return key;
    }
}
