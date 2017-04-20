package com.dingfei.api.util;

/**
 * Paging
 */
public class Paging {
    private int limit;
    private int offset;
    private int pages;
    private int count;
    
    public Paging() {
        super();
    }

    /**
     * Constructor
     *
     * @param limit int
     * @param offset int
     * @param pages int
     * @param count int
     */
    public Paging(int limit, int offset, int pages, int count) {
        this.limit = limit;
        this.offset = offset;
        this.pages = pages;
        this.count = count;
    }

    
    /**
     * getLimit
     *
     * @return int
     */
    public int getLimit() {
        return limit;
    }

    /**
     * setLimit
     *
     * @param limit int
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * getOffset
     *
     * @return int
     */
    public int getOffset() {
        return offset;
    }

    /**
     * setOffset
     *
     * @param offset int
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * getPages
     *
     * @return int
     */
    public int getPages() {
        return pages;
    }

    /**
     * setPages
     *
     * @param pages int
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * getCount
     *
     * @return int
     */
    public int getCount() {
        return count;
    }

    /**
     * setCount
     *
     * @param count int
     */
    public void setCount(int count) {
        this.count = count;
    }
}
