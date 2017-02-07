package com.chan.nerakanakku.util;

/**
 * Created by chandra-1765 on 2/4/17.
 */

public class NeraKanakkuUtil
{
    public static String convertMilliSeconds(long milliSeconds)
    {
        int seconds = (int) (milliSeconds / 1000);
        int minutes = seconds / 60;
        int hours = seconds / (60 * 60);
        seconds = seconds % 60;
        if(hours == 0 && minutes == 0)
        {
            return (String.format("00:00:%02d", seconds)); //No I18N
        }
        else if(hours == 0)
        {
            return (String.format("00:%02d:%02d", minutes, seconds)); //No I18N
        }
        else
        {
            return (String.format("%02d:%02d:%02d", hours, minutes, seconds)); //No I18N
        }
    }
}
