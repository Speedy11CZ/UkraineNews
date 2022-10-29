package cz.speedy11.ukrainenewsnotifier.storage.database;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Row {

    private final Map<String, Object> cells = new HashMap<>();

    public void addCell(String key, Object value) {
        cells.put(key, value);
    }


    public Object getObject(String key) {
        return cells.getOrDefault(key, null);
    }

    public String getString(String key) {
        Object obj = cells.get(key);
        return obj == null ? null : obj.toString();
    }

    public int getInt(String key) {
        return (int) cells.getOrDefault(key, null);
    }

    public long getLong(String key) {
        return (long) cells.getOrDefault(key, null);
    }

    public double getDouble(String key) {
        return (double) cells.getOrDefault(key, null);
    }

    public float getFloat(String key) {
        return (float) cells.getOrDefault(key, null);
    }

    public boolean getBoolean(String key) {
        return (boolean) cells.getOrDefault(key, null);
    }

    public Timestamp getTimestamp(String key) {
        return (Timestamp) cells.getOrDefault(key, null);
    }

    public Date getDate(String key) {
        return (Date) cells.getOrDefault(key, null);
    }

    public Blob getBlob(String key) {
        return (Blob) cells.getOrDefault(key, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return Objects.equals(cells, row.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public String toString() {
        return "Row{" +
                "cells=" + cells +
                '}';
    }
}
