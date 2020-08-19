package com.git.easyloan.entity;

import java.util.List;

/**
 * su 20191009
 * 为了解决在mybatis的分页获取数据中，需要用到foreach语句
 */
public class PageForMybatisPlugin extends Pages {

    private List<String> myLists;

    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<String> getMyLists() {
        return myLists;
    }

    public void setMyLists(List<String> myLists) {
        this.myLists = myLists;
    }


}
