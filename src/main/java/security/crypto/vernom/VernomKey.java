package security.crypto.vernom;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class VernomKey {

    @Getter
    @Setter
    private byte[] keyBits;

    public VernomKey(byte[] keyBits) {
        if (keyBits.length < 37) {
            throw new IllegalArgumentException("keyBits.length < 37");
        }
        this.keyBits = keyBits;
    }

    public VernomKey(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file);
             Scanner scanner = new Scanner(inputStream)) {
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            keyBits = parseStringToBits(scanner.nextLine());
        }
    }

    public VernomKey(String strBits) {
        keyBits = parseStringToBits(strBits);
    }

    private byte[] parseStringToBits(String str) {
        if (str.length() < 37) {
            throw new IllegalArgumentException("keyBits.length < 37");
        }
        return VernomUtils.parseStringToBits(str);
    }
}
