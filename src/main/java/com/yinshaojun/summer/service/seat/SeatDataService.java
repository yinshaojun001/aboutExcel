package com.yinshaojun.summer.service.seat;

import com.yinshaojun.summer.model.SeatStaticData;
import com.yinshaojun.summer.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatDataService implements DataService<List<SeatStaticData>> {

    @Override
    public List<SeatStaticData> dataClean(List<SeatStaticData> seatStaticDatas) {
        //最终结果
        List<SeatStaticData> newList = new ArrayList<>(200);
        //按航司分组
        Map<String, List<SeatStaticData>> airlineCollect = seatStaticDatas.stream().collect(Collectors.groupingBy(SeatStaticData::getAirline));

        airlineCollect.forEach((airline, airlineGroup) -> {
            //按锁座属性分组
            Map<String, List<SeatStaticData>> lockTypeCollect = airlineGroup.stream().collect(Collectors.groupingBy(SeatStaticData::getLockType));

            lockTypeCollect.forEach((lockType, lockTypeGroup) -> {
                //按机型分组
                Map<String, List<SeatStaticData>> equipCollect = lockTypeGroup.stream().collect(Collectors.groupingBy(SeatStaticData::getDepEquip));

                equipCollect.forEach((equip, equipGroup) -> {
                    HashMap<String, SeatStaticData> map = new HashMap<>(20);

                    equipGroup.forEach(seatStaticDataTemp -> {
                        if (map.containsKey(seatStaticDataTemp.getSeatNo())) {
                            //map中已经存放了相同的锁座项，要进行两条数据的合并
                            SeatStaticData existData = map.get(seatStaticDataTemp.getSeatNo());
                            //如果飞机注册号相同，就覆盖,不相同，就用逗号拼接
                            if (existData.getAirReg().equals(seatStaticDataTemp.getAirReg())) {
                                existData.setAirReg(seatStaticDataTemp.getAirReg());
                                existData.setDepVer(existData.getDepVer() + "," + seatStaticDataTemp.getDepVer());
                            } else {
                                existData.setAirReg(existData.getAirReg() + "," + seatStaticDataTemp.getAirReg());
                                existData.setDepVer(existData.getDepVer() + "," + seatStaticDataTemp.getDepVer());
                            }
                            map.put(seatStaticDataTemp.getSeatNo(), existData);
                        } else {
                            map.put(seatStaticDataTemp.getSeatNo(), seatStaticDataTemp);
                        }
                    });
                    newList.addAll(map.values());
                });
            });
        });
        //二次加工
        secDataClean(newList);
        return newList;
    }

    /**
     * 二次加工.
     *
     * @param seatStaticDatas 对离港版本二次加工
     */
    public List<SeatStaticData> secDataClean(List<SeatStaticData> seatStaticDatas) {
        seatStaticDatas.forEach(seatStaticData -> {
            if (seatStaticData.getDepVer().contains(",")) {
                Set<String> set = new HashSet<>(Arrays.asList(seatStaticData.getDepVer().split(",")));
                seatStaticData.setDepVer(String.join(",", set));
            }
        });
        return seatStaticDatas;
    }
}
