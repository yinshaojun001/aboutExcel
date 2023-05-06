package com.yinshaojun.summer.service;


public interface DataService<T> {

    /**
     * 数据清洗
     * @return 实体类
     */
    T dataClean(T t);

}
