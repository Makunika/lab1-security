package security.bi;

import lombok.Data;

@Data
public class BiKey {
    private final int rowCount;
    private final int columnCount;
    private final String letter;
}
