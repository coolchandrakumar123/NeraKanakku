/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chan.nerakanakku.timer;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.chan.nerakanakku.util.NeraKanakkuUtil;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Listens to user actions from the UI ({@link TimerFragment}), retrieves the data and updates the UI as required.
 */

public class TimerPresenter implements TimerContract.Presenter
{

    private final TimerContract.View timerView;
    private ScheduledFuture<?> scheduledFuture;

    public TimerPresenter(@NonNull TimerContract.View timerView)
    {
        this.timerView = timerView;
    }

    @Override
    public void processFabClick(View view)
    {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void startTimer(TextView timerTextView, long startMillis)
    {
        ScheduledThreadPoolExecutor  schedulingThread = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        scheduledFuture = schedulingThread.scheduleAtFixedRate(new ChanTimerRunnable(timerTextView, startMillis), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void stopTimer()
    {
        if(scheduledFuture != null && !scheduledFuture.isCancelled())
        {
            scheduledFuture.cancel(true);
        }
    }

    class ChanTimerRunnable implements Runnable
    {
        private WeakReference<TextView> weakReference;
        private long startMillis;

        public ChanTimerRunnable(TextView timerText, long startMillis)
        {
            weakReference = new WeakReference<TextView>(timerText);
            this.startMillis = startMillis;
        }

        @Override
        public void run()
        {
            long currentMillis = System.currentTimeMillis();
            final String timerStr = NeraKanakkuUtil.convertMilliSeconds(currentMillis - startMillis);
            final TextView timerText = weakReference.get();
            if(timerText != null)
            {
                timerText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerText.setText(timerStr);
                    }
                }, 0);
            }
        }

        private String convertMilliSeconds(long milliSeconds)
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

}
