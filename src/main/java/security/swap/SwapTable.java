package security.swap;

import lombok.Builder;
import lombok.Data;
import security.base.Alphabet;
import security.base.Table;
import security.bi.BiAlphabet;
import security.bi.BiTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public abstract class SwapTable<K, V> implements Table<K, V> {

    protected boolean isInit;
    protected char[][] table;
    protected Map<Character, SwapTable.Coordinate> coordinateMap;
    protected K key;

    public SwapTable(K key) {
        this.key = key;
        isInit = false;
    }

    @Override
    public void setKey(K key) {
        isInit = false;
        this.key = key;
    }

    @Override
    public K getKey() {
        return key;
    }

    protected abstract int getRowCount();
    protected abstract int getColumnCount();
    protected abstract String getSecret();
    protected abstract Alphabet getAlphabet();

    @Override
    public void init() {
        coordinateMap = new HashMap<>();
        table = new char[getRowCount()][getColumnCount()];
        LinkedHashSet<Character> alphabet = getAlphabet().getAlphabet();
        LinkedHashSet<Character> sortedTable = new LinkedHashSet<>();
        String keyUpperCase = getSecret().trim().toUpperCase();
        for (int i = 0; i < keyUpperCase.length(); i++) {
            sortedTable.add(keyUpperCase.charAt(i));
        }
        sortedTable.addAll(alphabet);
        if (getColumnCount() * getRowCount() != alphabet.size()) {
            throw new IllegalStateException("key.getWidth() * key.getHeight() != alphabet.size()");
        }

        Iterator<Character> iterator = sortedTable.iterator();

        System.out.print("[INFO] --  j =   | ");
        for (int i = 0; i < getColumnCount(); i++) {
            System.out.print("   " + i + "   | ");
        }
        System.out.println();
        for (int i = 0; i < getRowCount(); i++) {
            System.out.print("[INFO] -- i = " + i + " >| ");
            for (int j = 0; j < getColumnCount(); j++) {
                table[i][j] = iterator.next();
                System.out.print(" <<" + String.valueOf(table[i][j]).toUpperCase() + ">> | ");
                coordinateMap.put(table[i][j], Coordinate.builder().rowIndex(i).columnIndex(j).build());
            }
            System.out.println();
        }
        isInit = true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Data
    @Builder
    protected static class Coordinate {
        private int rowIndex;
        private int columnIndex;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
        }
    }
}
