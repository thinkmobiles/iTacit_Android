package com.itacit.healthcare.domain.models;

public class GetListParams {
        private Integer startIndex;
        private Integer rowCounts;

        public Integer getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(Integer startIndex) {
            this.startIndex = startIndex;
        }

        public Integer getRowCounts() {
            return rowCounts;
        }

        public void setRowCounts(Integer rowCounts) {
            this.rowCounts = rowCounts;
        }
    }