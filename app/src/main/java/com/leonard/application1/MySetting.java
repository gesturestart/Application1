package com.leonard.application1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 表情商店 on 2015/7/17.
 * 设置界面
 */
public class MySetting extends Activity{
    DevicePolicyManager dpManager;
    ComponentName myReceiver;
    boolean active;

    Button Button01;
    Button Button02;
    EditText EditText01;
    Button Button03;
    Button Button04;
    Button Button05;
    EditText EditText02;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        dpManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        myReceiver = getIntent().getParcelableExtra(
                DevicePolicyManager.EXTRA_DEVICE_ADMIN);

        active = dpManager.isAdminActive(myReceiver);

        Button01 = (Button) findViewById(R.id.Button01);
        Button02 = (Button) findViewById(R.id.Button02);
        EditText01 = (EditText) findViewById(R.id.EditText01);
        Button03 = (Button) findViewById(R.id.Button03);
        Button04 = (Button) findViewById(R.id.Button04);
        Button05 = (Button) findViewById(R.id.Button05);
        EditText02 = (EditText) findViewById(R.id.EditText02);

        Button01.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (active)
                {
                    // 锁屏幕
                    dpManager.lockNow();
                }
            }

        });

        Button02.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                int num = 0;
                String numStr = "" + EditText01.getText();
                if (!numStr.equals(""))
                {
                    num = Integer.parseInt(numStr);
                }
                if (active && num > 0)
                {
                    // 设定密码输入错误次数
                    dpManager.setMaximumFailedPasswordsForWipe(myReceiver, num);
                }
            }

        });

        Button03.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (active)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            MySetting.this);
                    builder.setMessage("将会删除手机的数据，确定吗？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // TODO Auto-generated method stub
                            // 清除手机数据
                            dpManager.wipeData(0);
                        }

                    });
                    builder.setNegativeButton("?", null);
                    builder.show();
                }
            }

        });

        Button04.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (active)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            MySetting.this);
                    builder.setMessage("将会删除手机及SD卡的数据，确定吗？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // TODO Auto-generated method stub
                            // 清除手机及SD卡数据
                            dpManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                        }

                    });
                    builder.setNegativeButton("?", null);
                    builder.show();
                }
            }

        });

        Button05.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                long timeSec = 0;
                String timeStr = "" + EditText02.getText();
                if (!timeStr.equals(""))
                {
                    timeSec = Long.parseLong(timeStr);
                }
                if (active)
                {
                    long timeMs = 1000 * timeSec;
                    // 设定锁屏幕的时间(毫秒)
                    dpManager.setMaximumTimeToLock(myReceiver, timeMs);
                }
            }

        });
    }
}
