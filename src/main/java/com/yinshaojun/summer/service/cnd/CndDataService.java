package com.yinshaojun.summer.service.cnd;


import com.yinshaojun.summer.model.CndStaticData;
import com.yinshaojun.summer.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CndDataService implements DataService<List<CndStaticData>> {

    @Override
    public List<CndStaticData> dataClean(List<CndStaticData> cndStaticDatas) {
        //转换成想要的格式之后存入这个list
        List<CndStaticData> newList = new ArrayList<>();
        //利用stream流来对集合中的元素进行分组，类似于sql的groupBy

        //按cnd分组
        Map<String, List<CndStaticData>> cndListMap;

        //按航司分组
        Map<String, List<CndStaticData>> airlineListMap;

        airlineListMap = cndStaticDatas.stream().collect(Collectors.groupingBy(CndStaticData::getAirline));

        for (Map.Entry<String, List<CndStaticData>> airlineEntry : airlineListMap.entrySet()) {

            //当前航司的所有cnd静态数据
            List<CndStaticData> cndStaticDataTemp = airlineEntry.getValue();
            //把当前的航司静态数据按照cnd进行groupBy
            cndListMap = cndStaticDataTemp.stream().collect(Collectors.groupingBy(CndStaticData::getCnd));

            for (Map.Entry<String, List<CndStaticData>> cndEntry : cndListMap.entrySet()) {
                List<CndStaticData> currentCndList = cndEntry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                CndStaticData cndStaticDataNew = new CndStaticData();
                for (CndStaticData cndStaticData : currentCndList) {
                    cndStaticDataNew = cndStaticData;
                    sb.append(cndStaticData.getAirReg()).append("#");
                }
                cndStaticDataNew.setAirReg(sb.toString());
                newList.add(cndStaticDataNew);
            }
        }

        return newList;
    }
}
