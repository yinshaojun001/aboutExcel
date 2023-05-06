package com.yinshaojun.summer.excel;

import com.yinshaojun.summer.model.ExcelData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExcelService {
     /**
      * 读.
      * @return 返回原始excel数据存入List
      */
     List<ExcelData> readExcel();


}
