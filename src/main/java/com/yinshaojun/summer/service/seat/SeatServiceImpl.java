package com.yinshaojun.summer.service.seat;

import com.yinshaojun.summer.excel.ExcelService;
import com.yinshaojun.summer.excel.impl.ExcelWriteService;
import com.yinshaojun.summer.model.ExcelData;
import com.yinshaojun.summer.model.SeatStaticData;
import com.yinshaojun.summer.service.ConvertDataService;
import com.yinshaojun.summer.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinshaojun
 */
@Service
public class SeatServiceImpl implements RuleService {

    private final ExcelService excelService;

    private final ConvertDataService<SeatStaticData> convertData2SeatService;

    private final SeatDataService seatDataService;

    private final ExcelWriteService writeService;

    /**
     * idea的改法,构造器.
     *
     * @param excelService            处理excel
     * @param convertData2SeatService 转换方法
     * @param seatDataService         清洗数据
     * @param writeService            写入excel
     */
    @Autowired
    public SeatServiceImpl(ExcelService excelService, ConvertDataService<SeatStaticData> convertData2SeatService, SeatDataService seatDataService, ExcelWriteService writeService) {
        this.excelService = excelService;
        this.convertData2SeatService = convertData2SeatService;
        this.seatDataService = seatDataService;
        this.writeService = writeService;
    }

    /**
     * 生成锁座规则
     */
    @Override
    public void produceRule() {
        /*读取原始excel数据*/
        List<ExcelData> excelData = excelService.readExcel();
        /*写入通用锁座规则*/
        classifyRule(excelData, SeatType.COMM.getType());
        /*写入国际锁座规则*/
        classifyRule(excelData, SeatType.INT.getType());
    }

    /**
     * 区分写入不同excel
     *
     * @param excelData
     */
    private void classifyRule(List<ExcelData> excelData, String type) {
        //单条excel可能变成多条锁座数据
        List<SeatStaticData> seatStaticData;
        //最后写入的总集合
        List<SeatStaticData> seatStaticDatas = new ArrayList<>(200);

        //循环出所有INT
        for (ExcelData temp : excelData) {
            if (temp.getAirportCode()!=null&&temp.getAirportCode().contains("INT")
                    && type.equals(SeatType.INT.getType())) {
                /*将excel元数据转换成非集中的静态锁座表*/
                seatStaticData = convertData2SeatService.excelData2List(temp);
                seatStaticDatas.addAll(seatStaticData);
            }
            if (type.equals(SeatType.COMM.getType())) {
                seatStaticData = convertData2SeatService.excelData2List(temp);
                seatStaticDatas.addAll(seatStaticData);
            }
        }
        /*数据清洗、改造、返回所有锁座静态数据*/
        seatStaticDatas = seatDataService.dataClean(seatStaticDatas);
        /*写入excel*/
        writeService.writeSeat(seatStaticDatas, type);
    }

}