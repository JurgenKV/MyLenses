package com.project.mylenses;

import android.icu.util.GregorianCalendar;
import android.icu.util.Calendar;

public class LensControl {
    private String countingMode;
    private Integer countUses;
    private Calendar nowDate;
    private Calendar endDate;

    public LensControl()
    {
        countUses = 0;
    }

    public LensControl(String countingMode,  Integer countUses, Calendar nowDate, Calendar endDate) {
        this.countUses = countUses;
        this.nowDate = nowDate;
        this.endDate = endDate;
        this.countingMode = countingMode;
    }


    public Integer getCountUses() {
        return countUses;
    }

    public void setCountUses(Integer nowCount) {
        this.countUses = nowCount;
    }

    public Calendar getNowDate() {
        return nowDate;
    }

    public void setNowDate(Calendar nowDate) {
        this.nowDate = nowDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getCountingMode(){ return countingMode;}

    public void setCountingMode(String countingMode){ this.countingMode = countingMode;}

    public void countUsesPlus()
    {
        countUses += 1;
    }

    public void countUsesMinus()
    {
        countUses -= 1;
    }

    public static Calendar createEndDate(Integer count, Calendar nowDay){
        Calendar endDay = nowDay;
        endDay.add(Calendar.DAY_OF_MONTH, count);
        return endDay;
    }
}
