package com.yinshaojun.summer.service.seat;

import com.yinshaojun.summer.model.ExcelData;
import com.yinshaojun.summer.model.LockSeatProp;
import com.yinshaojun.summer.model.LockSeatType;
import com.yinshaojun.summer.model.SeatStaticData;
import com.yinshaojun.summer.service.ConvertDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConvertData2SeatService implements ConvertDataService<SeatStaticData> {
    /**
     * @param excelData 原始读入进来的excel对象
     * @return null
     */
    @Override
    public SeatStaticData convertExcelData(final ExcelData excelData) {
        return null;
    }

    /**
     * @param excelData 原始读入进来的excel对象
     *                  {
     *                  "airReg": "1891",
     *                  "airline": "3U",
     *                  "blockSeat": "BS:31A|31C|31B|31J|31H|31K|32A|32C|32B|32J|32H|32K|33A|33C|33B|33J|33H|33K",
     *                  "cnd": "187",
     *                  "date": "2022.11.20",
     *                  "equipAndVer": "321/23B3",
     *                  "sU": "SU-C/9B-F",
     *                  "saf": "31A|31C|31H|61C|61H"
     *                  }
     * @return
     */
    @Override
    public List<SeatStaticData> excelData2List(final ExcelData excelData) {

        //一个元对象可以拼成多个锁座对象，再拼到List里
        List<SeatStaticData> seatStaticDatas = new ArrayList<>();

        //国际锁座list
        List<SeatStaticData> intSeatStaticDatas = new ArrayList<>();

        //锁座类型BLS SAF LTS
        if (excelData.getBlockSeat() != null) {
            SeatStaticData seatStaticData = buildBLSData(excelData);
            seatStaticDatas.add(seatStaticData);
        }

        if (excelData.getSaf() != null) {
            SeatStaticData seatStaticData = buildSafData(excelData);
            seatStaticDatas.add(seatStaticData);
        }

        if (excelData.getSU() != null) {
            SeatStaticData seatStaticData = buildLtsData(excelData);
            seatStaticDatas.add(seatStaticData);
        }
        return seatStaticDatas;
    }

    /**
     * 构建坏座对象.
     *
     * @param excelData data
     * @return data
     */
    private SeatStaticData buildBLSData(final ExcelData excelData) {
        SeatStaticData seatStaticData = buildCommonProp(excelData);
        //设置坏座BLS
        seatStaticData.setLockType(LockSeatType.BLS.toString());
        //属性
        seatStaticData.setLockProp(LockSeatProp.BLS.toString());
        //坏座的座位号
        seatStaticData.setSeatNo(excelData.convert2BlockSeatNo(excelData.getBlockSeat()));

        return seatStaticData;
    }

    /**
     * 构建安全员座椅对象.
     *
     * @param excelData data
     * @return data
     */
    private SeatStaticData buildSafData(final ExcelData excelData) {
        SeatStaticData seatStaticData = buildCommonProp(excelData);
        //设置安全员座位SAF
        seatStaticData.setLockType(LockSeatType.SAF.toString());
        //属性
        seatStaticData.setLockProp(LockSeatProp.BLS.toString());
        //坏座的座位号
        seatStaticData.setSeatNo(excelData.convert2Saf(excelData.getSaf()));

        return seatStaticData;
    }

    /**
     * 构建C锁对象.
     *
     * @param excelData data
     * @return data
     */
    private SeatStaticData buildLtsData(final ExcelData excelData) {
        SeatStaticData seatStaticData = buildCommonProp(excelData);
        seatStaticData.setLockType(LockSeatType.LTS.toString());
        seatStaticData.setLockProp(LockSeatProp.LTS.toString());
        seatStaticData.setSeatNo(excelData.convert2LTS(excelData.getSU()));
        return seatStaticData;

    }

    /**
     * 构建常规属性.
     *
     * @param excelData old data
     * @return seatStaticData
     */
    private SeatStaticData buildCommonProp(final ExcelData excelData) {

        SeatStaticData seatStaticData = new SeatStaticData();
        //航司
        if (excelData.getAirline() != null) {
            seatStaticData.setAirline(excelData.getAirline());
        } else {
            log.info("该条数据没有航司");
            seatStaticData.setAirline("");
        }
        //注册号
        if (excelData.getAirReg() != null) {
            seatStaticData.setAirReg("B"+excelData.getAirReg());
        }
        //到达站都统一为空字符串
        seatStaticData.setArrAirport("");
        //始发站统一为HRB
        seatStaticData.setDepAirport("HRB");

        if (excelData.getEquipAndVer() != null) {
            //离港机型
            seatStaticData.setDepEquip(excelData.getEquip(excelData.getEquipAndVer()));
            //离港机型版本
            seatStaticData.setDepVer(excelData.getVer(excelData.getEquipAndVer()));
        } else {
            log.info("该条数据没有离港机型和机型版本");
            seatStaticData.setDepEquip("");
            seatStaticData.setDepVer("");
        }
        //起始日期
        seatStaticData.setStartDate("28NOV22");
        //截止日期
        seatStaticData.setEndDate("28NOV99");

        return seatStaticData;
    }
}
