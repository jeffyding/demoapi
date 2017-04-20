package com.dingfei.api.util;

import java.util.List;

/**
 * PagingList
 */
public class PagingList<T> {
    
    
    
    public PagingList() {
        super();
    }

    private List<T> datas;
    private Paging paging;
   
    /**
     * Constructor
     *
     * @param datas  List
     * @param paging Paging
     */
    public PagingList(List<T> datas, Paging paging) {
        this.datas = datas;
        this.paging = paging;
    }

    /**
     * getDatas
     *
     * @return List
     */
    public List<T> getDatas() {
        return datas;
    }

    /**
     * setDatas
     *
     * @param datas List
     */
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * getPaging
     *
     * @return Paging
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * setPaging
     *
     * @param paging Paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}
