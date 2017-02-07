package com.chan.nerakanakku.timer;

import android.os.CountDownTimer;

/**
 * Created by chandra-1765 on 2/4/17.
 */

public class ChanCounter extends CountDownTimer
{
    public ChanCounter(long startTime, long interval)
    {
        super(startTime, interval);
    }

    @Override
    public void onFinish()
    {
        //text.setText("Time's up!");
        //timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        //text.setText("Time remain:" + millisUntilFinished);
        //timeElapsed = startTime - millisUntilFinished;
        //timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
    }
}
