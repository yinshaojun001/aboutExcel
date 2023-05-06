package com.yinshaojun.summer.service.seat;

/**
 * @author yinshaojun
 */
public enum SeatType {
    /*
     * 国际
     */
    INT("int"),
    /*
     *通用
     */
    COMM("comm");
    private String type;

    SeatType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
