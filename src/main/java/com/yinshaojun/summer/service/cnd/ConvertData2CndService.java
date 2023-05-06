package com.yinshaojun.summer.service.cnd;

import com.yinshaojun.summer.model.ExcelData;
import com.yinshaojun.summer.model.CndStaticData;
import com.yinshaojun.summer.service.ConvertDataService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * 转换类，把原来的excel里的数据转行成郭素要的那种格式
 * @author yinshaojun
 *
 */
@Service
@Slf4j
public class ConvertData2CndService implements ConvertDataService<CndStaticData> {

    /**
     * 把excel对象转换成总结的cnd静态数据
     * @see CndStaticData
     * @return cndStaticData
     */
    @Override
    public CndStaticData convertExcelData(ExcelData excelData){

        CndStaticData cndStaticData = new CndStaticData();

        if(excelData.getAirline()!=null){
            cndStaticData.setAirline(excelData.getAirline());
        } else {
            cndStaticData.setAirline("");
        }
        if(excelData.getEquipAndVer()!=null){
            //市场机型就是离港机型
            cndStaticData.setMarketEquip(excelData.getEquip(excelData.getEquipAndVer()));
            //离港机型
            cndStaticData.setDepEquip(excelData.getEquip(excelData.getEquipAndVer()));
            //离港机型版本
            cndStaticData.setDepVer(excelData.getVer(excelData.getEquipAndVer()));
        }else{
            log.info("该条数据没有机型版本");
            cndStaticData.setDepEquip("");
            cndStaticData.setDepVer("");
            cndStaticData.setMarketEquip("");
        }
        //飞机注册号
        String airReg ="B"+excelData.getAirReg();
        cndStaticData.setAirReg(airReg);
        //cnd
        if(excelData.getCnd()!=null){
            cndStaticData.setCnd(excelData.getCnd());
        }else {
            cndStaticData.setCnd("");
        }
        //创建时间写死
        cndStaticData.setCreateDate("2022-11-29");
        //航线ID
        if(excelData.getAirportCode()!=null){
            String routeId = MessageFormat.format("#{0}航司HRB始发{1}航线ID#", excelData.getAirline(), excelData.judgeINT(excelData.getAirportCode()));
            cndStaticData.setRouteId(routeId);
        }else{
            String routeId = MessageFormat.format("#{0}航司HRB始发{1}航线ID#", excelData.getAirline(), "通用");
            cndStaticData.setRouteId(routeId);
        }
        //groupID
        String groupId= MessageFormat.format("HRB机场{0}航班",excelData.getAirline());
        cndStaticData.setGroupID(groupId);
        //开始日期 写死
        cndStaticData.setStartDate("2022-11-29");
        //结束时间 写死
        cndStaticData.setEndDate("2099-12-31");
        //班期 写死 1234567
        cndStaticData.setFrequency("1234567");

        return cndStaticData;
    }

    @Override
    public List<CndStaticData> excelData2List(ExcelData excelData) {
        return null;
    }
}
