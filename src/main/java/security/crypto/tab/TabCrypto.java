package security.crypto.tab;

import security.crypto.base.Crypto;
import security.exceptions.CryptoException;

public class TabCrypto implements Crypto<TabKey> {

    private TabKey key;
    private TabTable table;

    public TabCrypto(TabKey key) {
        this.key = key;
        table = new TabTable(key);
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        if (key == null) {
            throw new CryptoException("key is null");
        }
        if (origin == null) {
            throw new CryptoException("origin is null");
        }
        int size = key.getColumnCount() * key.getRowCount();
        int offset = 0;
        StringBuilder sb = new StringBuilder();
        if (origin.length() <= size) {
            sb.append(table.encrypt(origin));
        } else {
            while (offset * size < origin.length()) {
                sb.append(table.encrypt(origin.substring(offset * size, Math.min(offset * size + size, origin.length()))));
                offset++;
                System.out.println("[INFO] -- block number " + offset);
            }
        }
        return sb.toString();
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        if (key == null) {
            throw new CryptoException("key is null");
        }
        if (crypto == null) {
            throw new CryptoException("crypto is null");
        }
        int size = key.getColumnCount() * key.getRowCount();
        int offset = 0;
        StringBuilder sb = new StringBuilder();
        if (crypto.length() <= size) {
            sb.append(table.decrypt(crypto));
        } else {
            while (offset * size < crypto.length()) {
                sb.append(table.decrypt(crypto.substring(offset * size, Math.min(offset * size + size, crypto.length()))));
                offset++;
                System.out.println("[INFO] -- block number " + offset);
            }
        }
        return sb.toString().trim();
    }

    @Override
    public void setKey(TabKey key) {
        this.key = key;
        table.setKey(key);
    }

    @Override
    public TabKey getKey() {
        return key;
    }
}
