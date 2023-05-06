package com.yinshaojun.summer.excel;

import com.yinshaojun.summer.model.ExcelData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExcelServiceImplTest {
    @Autowired
    ExcelService excelService;
    @Test
    void readExcel() {

        List<ExcelData> excelData = excelService.readExcel();
        for (ExcelData data:excelData) {
            if (data.getAirReg().equalsIgnoreCase("30D6")){
                System.out.println(data);
            }
        }
    }
}