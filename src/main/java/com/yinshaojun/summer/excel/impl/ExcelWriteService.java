package com.yinshaojun.summer.excel.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.yinshaojun.summer.model.CndStaticData;
import com.yinshaojun.summer.model.SeatStaticData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelWriteService {

    public void writeSeat(List<SeatStaticData> data,String type){
        String fileName="D:\\服务器\\summer\\src\\main\\resources\\static\\"+type+"seatstaticdata1229.xls";
        EasyExcelFactory.write(fileName, SeatStaticData.class)
                .sheet(type)
                .doWrite(() -> {
                    // 分页查询数据
                    return data;
                });
    }

    public void writeCnd(List<CndStaticData> data){
        String fileName="D:\\服务器\\summer\\src\\main\\resources\\static\\cndstaticdata1229.xls";
        EasyExcelFactory.write(fileName, CndStaticData.class)
                .sheet("模板")
                .doWrite(() -> {
                    // 分页查询数据
                    return data;
                });
    }
}
