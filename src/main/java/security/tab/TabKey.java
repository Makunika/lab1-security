package security.tab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


@Getter
@Setter
public class TabKey {
    private int rowCount;
    private int columnCount;

    public TabKey(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public TabKey(File file) throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream)) {
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            rowCount = scanner.nextInt();
            if (!scanner.hasNext()) {
                throw new IllegalStateException("file " + file.getAbsolutePath() + " not valid for key");
            }
            columnCount = scanner.nextInt();
        }
    }
}
