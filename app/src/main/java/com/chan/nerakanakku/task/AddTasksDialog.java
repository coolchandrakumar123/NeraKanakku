package com.chan.nerakanakku.task;

import com.chan.nerakanakku.R;
import com.chan.nerakanakku.util.NeraKanakkuSharedPrefs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by chandra-1765 on 2/4/17.
 */

public class AddTasksDialog extends AlertDialog
{

    private Context context;
    private Builder addTaskDialogBuilder;

    private EditText taskItemEdit;
    private TaskListener taskListener;

    private OnClickListener cancelClickListener = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };

    private OnClickListener doneClickListener = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            String newTask = taskItemEdit.getText().toString();
            if(newTask != null && newTask.length() > 0)
            {
                NeraKanakkuSharedPrefs.addTask(context, newTask);
                if(taskListener != null)
                {
                    taskListener.onTaskSelected(newTask);
                }
            }
            dialog.dismiss();
        }
    };

    public AddTasksDialog(Context context, TaskListener taskListener)
    {
        super(context);
        this.context = context;
        this.taskListener = taskListener;
        init();
    }

    private void init()
    {
        addTaskDialogBuilder = new Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.add_task_item, null);
        taskItemEdit = (EditText) dialogView.findViewById(R.id.task_item_edit);

        addTaskDialogBuilder.setView(dialogView);
        addTaskDialogBuilder.setTitle("Add Task");
        //addTaskDialogBuilder.setMessage("Enter Task");

        addTaskDialogBuilder.setNegativeButton("cancel", cancelClickListener);
        addTaskDialogBuilder.setPositiveButton("Done", doneClickListener);
    }

    public void show()
    {
        addTaskDialogBuilder.show();
    }

}
