package security.tab;

import security.base.Table;

import java.util.Arrays;

public class TabTable implements Table<TabKey, String> {

    private TabKey key;
    private char[][] table;

    public TabTable(TabKey key) {
        this.key = key;
        init();
    }

    @Override
    public void setKey(TabKey key) {
        this.key = key;
        init();
    }

    @Override
    public TabKey getKey() {
        return key;
    }

    @Override
    public void init() {
        table = new char[key.getRowCount()][key.getColumnCount()];
        clear();
    }

    @Override
    public boolean isInit() {
        return true;
    }

    @Override
    public String encrypt(String value) {
        clear();
        if (value.length() > key.getColumnCount() * key.getRowCount()) {
            throw new IllegalArgumentException("value.length() > key.getColumnCount() * key.getRowCount()");
        }
        int index = 0;
        for (int i = 0; i < key.getColumnCount(); i++) {
            for (int j = 0; j < key.getRowCount(); j++) {
                if (index >= value.length()) {
                    break;
                }
                table[j][i] = value.charAt(index++);
            }
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < key.getRowCount(); i++) {
            for (int j = 0; j < key.getColumnCount(); j++) {
                sb.append(table[i][j]);
            }
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String value) {
        clear();
        if (value.length() > key.getColumnCount() * key.getRowCount()) {
            throw new IllegalArgumentException("value.length() > key.getColumnCount() * key.getRowCount()");
        }
        int index = 0;
        for (int i = 0; i < key.getRowCount(); i++) {
            for (int j = 0; j < key.getColumnCount(); j++) {
                if (index >= value.length()) {
                    break;
                }
                table[i][j] = value.charAt(index++);
            }
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < key.getColumnCount(); i++) {
            for (int j = 0; j < key.getRowCount(); j++) {
                sb.append(table[j][i]);
            }
        }
        return sb.toString();
    }

    private void clear() {
        for (char[] chars : table) {
            Arrays.fill(chars, ' ');
        }
    }
}
