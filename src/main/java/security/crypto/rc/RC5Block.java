package security.crypto.rc;

import security.crypto.base.Table;
import security.exceptions.CryptoException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Maxim Pshiblo
 */
public class RC5Block implements Table<RC5Key, String> {

    private RC5Key key;
    private boolean isInit;
    private final List<Integer> L;
    private final List<Integer> S;
    private int P;
    private int Q;

    public RC5Block(RC5Key key) {
        this.key = key;
        isInit = false;
        L = new ArrayList<>();
        S = new ArrayList<>();
    }

    @Override
    public void init() throws CryptoException {
        if (key.getKey().length() > 255) {
            throw new CryptoException("key.getKey().length() > 255");
        }
        System.out.println("-----------INIT-----------");
        System.out.println("Ключ и его данные: \n" + key.toString());
        System.out.println("Процедура выравнивания ключа\n");
        System.out.println("1. Выравнивания ключа");

        StringBuilder sbKey = new StringBuilder(this.key.getKey());
        int c;
        if (sbKey.length() == 0) {
            sbKey.append("\0\0\0\0");
            c = 1;
        } else if (sbKey.length() % this.key.getW8() != 0) {
            sbKey.append("\0".repeat(key.getW8() - sbKey.length() % key.getW8()));
            c = sbKey.length() / key.getW8();
        } else {
            c = sbKey.length() / key.getW8();
        }
        String key = sbKey.toString();
        System.out.println("Ключ, длина которого теперь выровнена имеет длину " + key.length() + " вместо " + this.key.getKey().length());
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i+=4) {
            L.add(((0xFF & bytes[i]) << 24) | ((0xFF & bytes[i + 1]) << 16) |
                    ((0xFF & bytes[i + 2]) << 8) | (0xFF & bytes[i + 3]));
        }
        System.out.println("Массив L (размер = " + L.size() + "):");
        for (int i = 0; i < L.size(); i++) {
            System.out.println("L[" + i + "] = " + Integer.toBinaryString(L.get(i)));
        }

        System.out.println("\n2. Инициализация массива расширенных ключей");
        P = 0xB7E15163; // P и W для w = 32
        Q = 0x9E3779B9;
        System.out.println("P (w = 32) = " + P);
        System.out.println("Q (w = 32) = " + Q);
        S.add(P);
        for (int i = 1; i < 4; i++) {
            S.add(S.get(i - 1) + Q);
        }
        System.out.println("Массив расширенных ключей (S): " + S);

        System.out.println("\n3. Перемешивание");
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        for (int k = 0; k < 3 * Math.max(c, 3); k++) {
            a = (S.get(i) + a + b) << 3;
            S.set(i, a);
            b = (L.get(j) + a + b) << (a + b);
            L.set(j, b);
            i = (i + 1) % 3;
            j = (j + 1) % c;
        }
        System.out.println("S = " + S);
        System.out.println("L = " + L);

        System.out.println("S[0] = " + S.get(0) + " " + Integer.toBinaryString(S.get(0)));
        System.out.println("S[1] = " + S.get(1) + " " + Integer.toBinaryString(S.get(1)));
        System.out.println("S[2*r(1)] = " + S.get(2) + " " + Integer.toBinaryString(S.get(2)));
        System.out.println("S[2*r(1)+1] = " + S.get(2+1) + " " + Integer.toBinaryString(S.get(2+1)));

        System.out.println("-----------INIT-----------");
        System.out.println();
    }

    @Override
    public String encrypt(String value) {
        if (value.length() > key.getW4()) {
            throw new IllegalArgumentException("value.length() > key.getW4()");
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length != 8) {
            bytes = Arrays.copyOf(bytes, 8);
        }
        int[] ints = new int[2];
        int j = 0;
        for (int i = 0; i < bytes.length; i+=4) {
            ints[j] = ((0xFF & bytes[i]) << 24) | ((0xFF & bytes[i + 1]) << 16) |
                    ((0xFF & bytes[i + 2]) << 8) | (0xFF & bytes[i + 3]);
            j++;
        }
        System.out.println("-------------------------");
        System.out.println("Строка для шифра: " + value);
        System.out.println("2 слова этой строки: " + ints[0] + " " + ints[1] +
                " (" + Integer.toBinaryString(ints[0]) + " " + Integer.toBinaryString(ints[1]) + ")");
        System.out.println("------НУЛЕВОЙ РАУНД------");
        int a = ints[0];
        int b = ints[1];
        System.out.println("a =                                       " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("b =                                       " + Integer.toBinaryString(b) + " (" + b + ")");
        System.out.println("Операция a = (a + s[0]) % 2^w");
        a = Math.floorMod((a + S.get(0)), key.getMod());
        System.out.println("(a + s[0]) % 2^w =                        " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("Операция b = (b + s[1]) % 2^w");
        b = Math.floorMod((b + S.get(1)), key.getMod());
        System.out.println("(b + s[1]) % 2^w =                        " + Integer.toBinaryString(b) + " (" + b + ")");
        System.out.println("------ПЕРВЫЙ РАУНД------");
        System.out.println("--->   a = (((a xor b) << b) + s[2 * 1]) % 2^w");
        System.out.println("a =                                       " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("b =                                       " + Integer.toBinaryString(b) + " (" + b + ")");
        System.out.println("a xor b =                                 " + Integer.toBinaryString((a ^ b)) + " (" + (a ^ b) + ")");
        System.out.println("((a xor b) << b) =                        " + Integer.toBinaryString(((a ^ b) << b)) + " (" + ((a ^ b) << b) + ")");
        System.out.println("(((a xor b) << b) + s[2 * 1]) =           " + Integer.toBinaryString((((a ^ b) << b) + S.get(2))) + " (" + (((a ^ b) << b) + S.get(2)) + ")");
        a = Math.floorMod(((a ^ b) << b) + S.get(2), key.getMod());
        System.out.println("(((a xor b) << b) + s[2 * 1]) % 2^w =     " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("--->   b = (((a xor b) << a) + s[2 * 1 + 1]) % 2^w");
        System.out.println("a =                                       " + Integer.toBinaryString(a) + " (" + a + ")");
        System.out.println("b =                                       " + Integer.toBinaryString(b) + " (" + b + ")");
        System.out.println("a xor b =                                 " + Integer.toBinaryString((a ^ b)) + " (" + (a ^ b) + ")");
        System.out.println("((a xor b) << a) =                        " + Integer.toBinaryString(((a ^ b) << a)) + " (" + ((a ^ b) << a) + ")");
        System.out.println("(((a xor b) << a) + s[2 * 1 + 1]) =       " + Integer.toBinaryString((((a ^ b) << a) + S.get(3))) + " (" + (((a ^ b) << a) + S.get(3)) + ")");
        b = Math.floorMod(((a ^ b) << a) + S.get(3), key.getMod());
        System.out.println("(((a xor b) << a) + s[2 * 1 + 1]) % 2^w = " + Integer.toBinaryString(b) + " (" + b + ")");
        System.out.println("-------------------------");
        byte[] array = ByteBuffer.allocate(8).putInt(a).putInt(b).array();
        String result = new String(array, StandardCharsets.UTF_8);
        System.out.println("Result:" + result);
        return result;
    }

    @Override
    public String decrypt(String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public void setKey(RC5Key key) {
        this.key = key;
        isInit = false;
    }

    @Override
    public RC5Key getKey() {
        return key;
    }
}
