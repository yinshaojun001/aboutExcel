package com.yinshaojun.summer.model;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 各种锁座的静态数据，给seat对象，入库.
 * @author yinshaojun
 *
 */
@Component
@Data
public class SeatStaticData {

    //航司
    @ExcelProperty("航司")
    private String airline;

    //离港机型
    @ExcelProperty("离港机型")
    private String depEquip;

    //离港版本
    @ExcelProperty("离港版本")
    private String depVer;

    //飞机注册号
    @ExcelProperty("飞机注册号")
    private String airReg;

    //锁座类型
    @ExcelProperty("锁座类型")
    private String lockType;

    //锁座属性
    @ExcelProperty("锁座属性")
    private String lockProp;

    //座位号
    @ExcelProperty("座位号")
    private String seatNo;

    //始发站
    @ExcelProperty("始发站")
    private String depAirport;

    //到达站
    @ExcelProperty("到达站")
    private String arrAirport;

    //开始日期
    @ExcelProperty("起飞日期")
    private String startDate;

    //结束日期
    @ExcelProperty("结束日期")
    private String endDate;

}
