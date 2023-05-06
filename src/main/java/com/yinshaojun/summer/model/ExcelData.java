package com.yinshaojun.summer.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 读取原EXCEL表中的所有列
 * excel表
 *
 * @author yinshaojun
 */
@Component
@Slf4j
@Data
public class ExcelData {

    //与excel第一列对应 航司，必须按照excel顺序
    @ExcelProperty("航司")
    private String airline;

    //飞机号
    @ExcelProperty("飞机号")
    private String airReg;

    //cnd
    @ExcelProperty("cnd")
    private String cnd;

    //目的地三字码
    @ExcelProperty("目的地三字码")
    private String airportCode;

    //座位
    @ExcelProperty("座位")
    private String blockSeat;

    //安全员座位
    @ExcelProperty("安全员座位")
    private String saf;

    //最新更新时间
    @ExcelProperty("最新更新时间")
    private String date;

    //机型/机型版本
    @ExcelProperty("机型")
    private String equipAndVer;

    //C锁
    @ExcelProperty("SU")
    private String SU;

    /**
     * 根据EXCEL中的离港机型合版本，分割成离港机型
     *
     * @param equipAndVer
     * @return
     */
    public String getEquip(String equipAndVer) {
        String[] equipOrVer = equipAndVer.split("/");
        return equipOrVer[0];
    }

    /**
     * 根据EXCEL中的离港机型合版本，分割成离港版本
     * @param equipAndVer
     * @return
     */
    public String getVer(String equipAndVer) {
        if (equipAndVer.contains("/")) {
            String[] equipOrVer = equipAndVer.split("/");
            return equipOrVer[1];
        }
        return equipAndVer;
    }

    /**
     * 判断国际国内航线
     * @param airportCode
     * @return
     */
    public String judgeINT(String airportCode) {
        if (airportCode.equals("INT")) {
            return RouteType.国际.toString();
        }
        return RouteType.通用.toString();
    }

    /**
     * 坏座格式修改
     *
     * @param blockSeat
     * @return
     */
    public String convert2BlockSeatNo(String blockSeat) {
        if (blockSeat.contains("BS:")) {
            blockSeat = blockSeat.replace("BS:", "");
        }
        if (blockSeat.contains("|")) {
            blockSeat = blockSeat.replace("|", ",");
        }
        return blockSeat;
    }

    /**
     * 安全员座位格式修改
     *
     * @param saf
     * @return
     */
    public String convert2Saf(String saf) {
        if (saf.contains("|")) {
            saf = saf.replace("|", ",");
        }
        if (saf.contains("、")) {
            saf = saf.replace("、", ",");
        }
        if (saf.contains("/")) {
            saf = saf.replace("/", ",");
        }
        if (saf.contains(".")) {
            saf = saf.replace(".", ",");
        }
        if (saf.contains(" ")) {
            saf = saf.replace(" ", ",");
        }


        return saf;
    }

    public String convert2LTS(String lts) {
        if (lts.contains("SU+C/")) {
            lts = lts.replace("SU+C/", "");
        }
        return lts;
    }
}
