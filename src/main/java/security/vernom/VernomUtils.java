package security.vernom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VernomUtils {

    public byte[] parseStringToBits(String str) {
        byte[] bits = new byte[str.length()];
        for (int i = 0; i < bits.length; i++) {
            byte b = Byte.parseByte(String.valueOf(str.charAt(i)));
            if (b != 0 && b != 1) {
                throw new IllegalArgumentException("str 0 or 1");
            }
            bits[i] = b;
        }
        return bits;
    }

    public String bitsToString(byte[] bits) {
        StringBuilder sb = new StringBuilder();
        for (byte bit : bits) {
            sb.append(String.valueOf(bit));
        }
        return sb.toString();
    }

    public static String bitsToString(byte[] bits, int start, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + count; i++) {
            sb.append(String.valueOf(bits[i]));
        }
        return sb.toString();
    }
}
