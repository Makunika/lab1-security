package security.crypto.enigma;

import security.crypto.base.Crypto;
import security.crypto.enigma.rotor.Rotor;
import security.exceptions.CryptoException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnigmaCrypto implements Crypto<EnigmaKey> {

    private EnigmaKey enigmaKey;
    private final List<Rotor> rotors;

    public EnigmaCrypto(EnigmaKey enigmaKey) {
        this.enigmaKey = enigmaKey;
        rotors = new ArrayList<>();
        rotors.add(new Rotor(enigmaKey.getKey1().toUpperCase()));
        rotors.add(new Rotor(enigmaKey.getKey2().toUpperCase()));
    }

    @Override
    public String encryption(String origin) throws CryptoException {
        for (Rotor rotor : rotors) {
            if (!rotor.isInit())
                rotor.init();
        }
        List<Character> characters = origin.toUpperCase().chars().mapToObj((ch) -> (char) ch).collect(Collectors.toList());
        List<Character> result = new ArrayList<>();
        Rotor rotor1 = rotors.get(0);
        Rotor rotor2 = rotors.get(1);
        for (Character character : characters) {
            result.add(rotor2.encrypt(rotor1.encrypt(character)));
            if (rotor1.incrementJ()) {
                rotor2.incrementJ();
            }
        }
        return result.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public String decryption(String crypto) throws CryptoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setKey(EnigmaKey key) {
        this.enigmaKey = key;
        rotors.get(0).setKey(enigmaKey.getKey1());
        rotors.get(1).setKey(enigmaKey.getKey2());
    }

    @Override
    public EnigmaKey getKey() {
        return enigmaKey;
    }
}