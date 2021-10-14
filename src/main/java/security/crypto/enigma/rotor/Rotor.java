package security.crypto.enigma.rotor;

import security.crypto.base.Table;
import security.exceptions.CryptoException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Rotor implements Table<String, Character> {

    private String key;
    private HashMap<Character, Character> map;
    private List<Character> alphabet;
    private boolean isInit;
    private int j;

    public Rotor(String key) {
        this.key = key;
        j = 0;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
        isInit = false;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void init() throws CryptoException {
        map.clear();
        this.alphabet.clear();
        List<Character> currentKey = key.chars().distinct().mapToObj((c) -> (char) c).collect(Collectors.toList());
        if (currentKey.size() != 35) {
            throw new CryptoException("key size != 35");
        }
        EnigmaAlphabet alphabet = EnigmaAlphabet.getInstance();
        if (!alphabet.isValid(currentKey.stream().map(String::valueOf).collect(Collectors.joining()))) {
            throw new CryptoException("key not valid!");
        }
        LinkedHashSet<Character> setAlphabet = alphabet.getAlphabet();
        int i = 0;
        for (Character character : setAlphabet) {
            this.alphabet.add(character);
            map.put(character, currentKey.get(i));
            i++;
        }
        isInit = true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public Character encrypt(Character value) {
        Character currentValue = alphabet.get((alphabet.indexOf(value) + alphabet.size() + j) % alphabet.size());
        return map.get(currentValue);
    }

    @Override
    public Character decrypt(Character value) {
        throw new UnsupportedOperationException();
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean incrementJ() {
        j++;
        return j % alphabet.size() == 0;
    }
}
