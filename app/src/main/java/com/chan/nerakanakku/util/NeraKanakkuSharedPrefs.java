package com.chan.nerakanakku.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chandra-1765 on 2/4/17.
 */

public class NeraKanakkuSharedPrefs
{
    public final static String SEPARATOR = "_CHAN_";
    private final static String SHARED_PREFS_NAME = "TimerSharedPrefs";
    private final static String TASKS_LIST_KEY = "TASKS_LIST_KEY";
    private final static String RUNNING_TASK = "RUNNING_TASK";

    private final static String TIMER_STATE = "TIMER_STATE";
    public final static String RUNNING = "RUNNING";
    public final static String STOPPED = "STOPPED";

    private final static String START_TIME = "START_TIME";
    private final static String WORK_TIME = "WORK_TIME";

    public static String getTasksList(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(TASKS_LIST_KEY, null);
    }

    public static void removeTasksList(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().remove(TASKS_LIST_KEY).commit();
    }

    public static void clearAllItems(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public static void addTask(Context context, String newTask)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String tasksList = sharedPref.getString(TASKS_LIST_KEY, null);
        if(tasksList == null)
        {
            tasksList = newTask;
        }
        else
        {
            tasksList = tasksList + NeraKanakkuSharedPrefs.SEPARATOR + newTask;
        }
        sharedPref.edit().putString(TASKS_LIST_KEY, tasksList).commit();
    }

    public static void setRunningTask(Context context, String task)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(RUNNING_TASK, task).commit();
    }

    public static String getRunningTask(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(RUNNING_TASK, null);
    }

    public static void removeRunningTask(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().remove(RUNNING_TASK).commit();
    }

    public static String getTimerState(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(TIMER_STATE, STOPPED);
    }

    public static void setTimerState(Context context, String timerState)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putString(TIMER_STATE, timerState).commit();
    }

    public static Long getTaskStartTime(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getLong(START_TIME, 0);
    }

    public static void setTaskStartTime(Context context, long startMillis)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putLong(START_TIME, startMillis).commit();
    }

    public static void removeTaskStartTime(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().remove(START_TIME).commit();
    }

    public static Long getTaskWorkTime(Context context, String task)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getLong(task + WORK_TIME, 0);
    }

    public static void setTaskWorkTime(Context context, String task, long startMillis)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        sharedPref.edit().putLong(task + WORK_TIME, startMillis).commit();
    }
}
