package com.yinshaojun.summer.service;

import com.yinshaojun.summer.model.ExcelData;

import java.util.List;

/**
 * @author yinshaojun
 * @param <T> 泛型
 */
public interface ConvertDataService<T> {

    /**
     * EXCEL数据转换类.
     * @param excelData 原始读入进来的excel对象
     * @return 实体类
     */
    T convertExcelData(ExcelData excelData);

    /**
     * 转换锁座规则.
     * @param excelData 原始读入进来的excel对象
     * @return 实体类
     */
    List<T> excelData2List(ExcelData excelData);
}
