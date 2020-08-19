package com.git.easyloan.entity;

public class Pages {

    private int showCount;
    private int totalPage;
    private int totalResult;
    private int currentPage;
    private int currentResult;
    private boolean entityOrField;
    private PageData pd = new PageData();

    public Pages() {
        try {
            this.showCount = 10;
        } catch (Exception var2) {
            this.showCount = 15;
        }

    }

    public int getTotalPage() {
        if (this.totalResult % this.showCount == 0) {
            this.totalPage = this.totalResult / this.showCount;
        } else {
            this.totalPage = this.totalResult / this.showCount + 1;
        }

        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return this.totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }

        if (this.currentPage > this.getTotalPage()) {
            this.currentPage = this.getTotalPage();
        }

        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getShowCount() {
        return this.showCount;
    }

    public void setShowCount(int showCount) {
        if (100 < showCount) {
            showCount = 100;
        }

        this.showCount = showCount;
    }

    public int getCurrentResult() {
        this.currentResult = (this.getCurrentPage() - 1) * this.getShowCount();
        if (this.currentResult < 0) {
            this.currentResult = 0;
        }

        return this.currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public boolean isEntityOrField() {
        return this.entityOrField;
    }

    public void setEntityOrField(boolean entityOrField) {
        this.entityOrField = entityOrField;
    }

    public PageData getPd() {
        return this.pd;
    }

    public void setPd(PageData pd) {
        this.pd = pd;
    }
}

