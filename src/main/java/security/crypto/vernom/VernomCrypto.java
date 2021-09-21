package security.crypto.vernom;

import security.crypto.base.Crypto;
import security.exceptions.CryptoException;

import java.util.Arrays;

public class VernomCrypto implements Crypto<VernomKey> {

    private VernomKey key;
    private int index;

    public VernomCrypto(VernomKey key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        this.key = key;
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        if (origin == null) {
            throw new CryptoException("origin is null");
        }

        VernomAlphabet alphabet = VernomAlphabet.getInstance();
        String originLowerCase = origin.toLowerCase();
        if (!alphabet.isValid(originLowerCase)) {
            throw new CryptoException("origin bot valid");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < originLowerCase.length(); i++) {
            char c = originLowerCase.charAt(i);
            byte[] bits = Arrays.copyOf(alphabet.getBitsMap().get(c), 6);
            sb.append(VernomUtils.bitsToString(encryption(bits)));
        }
        index = 0;
        return sb.toString();
    }

    private byte[] encryption(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = xor(array[i], nextKeyBit());
        }
        return array;
    }

    private byte nextKeyBit() {
        if (index == key.getKeyBits().length) {
            index = 0;
        }
        return key.getKeyBits()[index++];
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        if (crypto == null) {
            throw new CryptoException("crypto is null");
        }
        if (crypto.length() % 6 != 0) {
            throw new CryptoException("% 6!");
        }
        byte[] bits = VernomUtils.parseStringToBits(crypto);
        for (int i = 0; i < bits.length; i++) {
            bits[i] = xor(bits[i], nextKeyBit());
        }
        VernomAlphabet alphabet = VernomAlphabet.getInstance();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bits.length; i+=6) {
            result.append(alphabet.getBitsMapString().get(VernomUtils.bitsToString(bits, i, 6)));
        }
        index = 0;
        return result.toString();
    }

    private byte xor(byte one, byte two) {
        return (byte) ((one + two) % 2);
    }

    @Override
    public void setKey(VernomKey key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        this.key = key;
    }

    @Override
    public VernomKey getKey() {
        return key;
    }
}
