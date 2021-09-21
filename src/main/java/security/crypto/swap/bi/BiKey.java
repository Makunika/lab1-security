package security.crypto.swap.bi;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

@Getter
@Setter
public class BiKey {
    private int rowCount;
    private int columnCount;
    private String letter;

    public BiKey(int rowCount, int columnCount, String letter) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.letter = letter;
    }

    public BiKey(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream)) {
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            rowCount = scanner.nextInt();
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            columnCount = scanner.nextInt();
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            scanner.nextLine();
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            letter = scanner.nextLine();
        }
    }

    public BiKey() {
    }

    @Override
    public String toString() {
        return "BiKey{" +
                "rowCount=" + rowCount +
                ", columnCount=" + columnCount +
                ", letter='" + letter + '\'' +
                '}';
    }
}
