package com.yinshaojun.summer.service.cnd;

import com.yinshaojun.summer.excel.ExcelService;
import com.yinshaojun.summer.excel.impl.ExcelWriteService;
import com.yinshaojun.summer.model.CndStaticData;
import com.yinshaojun.summer.model.ExcelData;
import com.yinshaojun.summer.service.ConvertDataService;
import com.yinshaojun.summer.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CndServiceImpl implements RuleService {
    private final ExcelService excelService;
    private final ConvertDataService<CndStaticData> convertData2CndService;
    private final CndDataService cndDataService;
    private final ExcelWriteService writeService;

    @Autowired
    public CndServiceImpl(ExcelService excelService, ConvertDataService<CndStaticData> convertData2CndService, CndDataService cndDataService, ExcelWriteService writeService) {
        this.excelService = excelService;
        this.convertData2CndService = convertData2CndService;
        this.cndDataService = cndDataService;
        this.writeService = writeService;
    }

    @Override
    public void produceRule() {
        /*读取原始excel数据*/
        List<ExcelData> excelData = excelService.readExcel();

        /*
         * 静态CND数据表
         */
        List<CndStaticData> cndLists = new ArrayList<>();

        /*
         * 将excel元数据转换成非集中的静态cnd表
         */
        for (ExcelData data : excelData) {
            CndStaticData cndStaticData = convertData2CndService.convertExcelData(data);
            cndLists.add(cndStaticData);
        }

        //拼接飞机号 返回目标格式的cnd静态规则
        List<CndStaticData> cndStaticData = cndDataService.dataClean(cndLists);
        //写
        writeService.writeCnd(cndStaticData);
    }
}
