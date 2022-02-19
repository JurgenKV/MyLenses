package com.project.mylenses;

import java.util.Date;

public class LensControl {

    private Integer nowCount;
    private Integer endCount;
    private Date nowDate;
    private Date endDate;

    public LensControl()
    {
        nowCount = 0;
        endCount = 0;
    }

    public LensControl(Integer nowCount, String up) {
        this.nowCount = nowCount;
        this.endCount = endCount;
        this.nowDate = nowDate;
        this.endDate = endDate;
    }

    public LensControl(Integer endCount) {
        this.nowCount = nowCount;
        this.endCount = endCount;
        this.nowDate = nowDate;
        this.endDate = endDate;
    }

    public LensControl(Integer nowCount, Integer endCount, Date nowDate, Date endDate) {
        this.nowCount = nowCount;
        this.endCount = endCount;
        this.nowDate = nowDate;
        this.endDate = endDate;
    }


    public Integer getNowCount() {
        return nowCount;
    }

    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void nowCountPlus()
    {
        nowCount += 1;
    }
    public void nowCountMinus()
    {
        nowCount -= 1;
    }


}
