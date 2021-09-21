package security.cracker;

import lombok.Data;

/**
 * @author Maxim Pshiblo
 */
@Data
public class CrackResult<K> {
    private K key;
    private String encrypt;
}
