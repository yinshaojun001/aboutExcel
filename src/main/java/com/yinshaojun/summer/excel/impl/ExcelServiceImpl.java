package com.yinshaojun.summer.excel.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.yinshaojun.summer.excel.ExcelService;
import com.yinshaojun.summer.model.ExcelData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel原信息，并改成静态数据的格式入库
 *
 * @author yinshaojun
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<ExcelData> readExcel() {
        String fileName = "D:\\服务器\\summer\\src\\main\\resources\\static\\cnd20170326.xls";
        /*
         * 将excel中的数据存到excel.
         */
        List<ExcelData> excelDataList = new ArrayList<>(200);
        /*
         *读取原excel数据
         */
        EasyExcelFactory.read(fileName, ExcelData.class, new ReadListener<ExcelData>() {
            /**
             * 单次缓存的数据量
             */
            private static final int BATCH_COUNT = 200;
            /**
             * 临时存储200条
             */
            private List<ExcelData> cacheExcelDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(ExcelData data, AnalysisContext analysisContext) {
                cacheExcelDataList.add(data);
                /*
                 *当读到200条时候，进行数据存储，并清洗数据
                 */
                if (cacheExcelDataList.size() >= BATCH_COUNT) {
                    //本来想直接存储数据库，但是数据格式不符合要求，需要二次加工
                    saveData();
                    // 存储完成清理 list
                    cacheExcelDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }
            /**
             * 存完所有数据之后要做的事儿
             * @param analysisContext text
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //存入所有转换后的cnd静态数据对象 cndLists
                saveData();
            }
            private void saveData() {
                //保存到list中
                excelDataList.addAll(cacheExcelDataList);
            }
        }).sheet().doRead();

        return excelDataList;
    }
}
