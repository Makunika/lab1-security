package security.tremus;

import security.base.Crypto;
import security.exceptions.CryptoException;

public class TremosCrypto implements Crypto<TremosKey> {

    private final TremosTable table;
    private TremosKey key;

    public TremosCrypto(TremosKey key) {
        this.key = key;
        table = new TremosTable(key);
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        if (!table.isInit()) {
            table.init();
        }
        return table.encrypt(origin);
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        if (!table.isInit()) {
            table.init();
        }
        return table.decrypt(crypto);
    }

    @Override
    public void setKey(TremosKey key) {
        table.setKey(key);
        this.key = key;
    }

    @Override
    public TremosKey getKey() {
        return key;
    }
}
