package com.yinshaojun.summer.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * CND的规则 给dst的对象 入库
 *
 * @author yinshaojun
 */
@Component
@Data
public class CndStaticData {

    //航司
    @ExcelProperty("航司")
    private String airline;

    //市场机型=离港机型
    @ExcelProperty("市场机型")
    private String marketEquip;

    //离港机型（可空）
    @ExcelProperty("离港机型")
    private String depEquip;

    //离港版本
    @ExcelProperty("离港版本")
    private String depVer;

    //飞机注册号
    @ExcelProperty("飞机注册号")
    private String airReg;

    //CND
    @ExcelProperty("cnd")
    private String cnd;

    //航线id
    @ExcelProperty("航线id")
    private String routeId;

    //开始日期
    @ExcelProperty("起飞时间")
    private String startDate;

    //截止日期
    @ExcelProperty("截止时间")
    private String endDate;

    //班期
    @ExcelProperty("班期")
    private String frequency;

    //创建时间
    @ExcelProperty("创建时间")
    private String createDate;

    //groupID
    @ExcelProperty("groupID")
    private String groupID;

}
