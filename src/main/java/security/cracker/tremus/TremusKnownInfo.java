package security.cracker.tremus;

import lombok.Data;
import security.cracker.base.KnownInfo;

/**
 * @author Maxim Pshiblo
 */
@Data
public class TremusKnownInfo implements KnownInfo {
    private String letter;
    private int rowCount;
}
