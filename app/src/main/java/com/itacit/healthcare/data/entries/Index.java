package com.itacit.healthcare.data.entries;

/**
 * Created by Den on 25.11.15.
 */
public class Index {
    private int startIndex;
    private int rowCount;
    private String filter;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int _startIndex) {
        startIndex = _startIndex;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int _rowCount) {
        rowCount = _rowCount;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String _filter) {
        filter = _filter;
    }
}
