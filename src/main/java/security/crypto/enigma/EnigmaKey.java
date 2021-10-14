package security.crypto.enigma;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EnigmaKey {

    private final String key1;
    private final String key2;

    public EnigmaKey(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @SneakyThrows
    public EnigmaKey(File file) {
        List<String> stringList = Files.lines(Path.of(file.toURI())).collect(Collectors.toList());
        key1 = stringList.get(0);
        key2 = stringList.get(1);
    }

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }

}
