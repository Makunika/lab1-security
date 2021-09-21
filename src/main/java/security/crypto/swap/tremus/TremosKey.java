package security.crypto.swap.tremus;

import security.crypto.swap.bi.BiKey;

import java.io.File;
import java.io.IOException;

public class TremosKey extends BiKey {
    public TremosKey(int rowCount, int columnCount, String letter) {
        super(rowCount, columnCount, letter);
    }

    public TremosKey(File file) throws IOException {
        super(file);
    }

    public TremosKey() {
    }
}
