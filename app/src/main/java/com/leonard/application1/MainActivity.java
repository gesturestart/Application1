package com.leonard.application1;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {
    private DevicePolicyManager dpManager;
    private ComponentName myReceiver;
    private Button start;
    private Button stop;
    private Button setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dpManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        myReceiver = new ComponentName(this, MyReceiver.class);
        start = (Button) findViewById(R.id.Button01);
        stop = (Button) findViewById(R.id.Button02);
        setting = (Button) findViewById(R.id.Button03);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            //启动设置界面
            case R.id.Button01:
                intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, myReceiver);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "额外说明：此画面为启动设备管理器画面");
                startActivityForResult(intent, 1);
                break;
            //停用设置
            case R.id.Button02:
                dpManager.removeActiveAdmin(myReceiver);
                updateButtonStates();
                break;
            case R.id.Button03:
                intent = new Intent();
                intent.setClass(MainActivity.this, MySetting.class);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        myReceiver);
                startActivity(intent);
        }
    }

    /**
     * 更新按钮的状态
     */
    private void updateButtonStates() {
        boolean active = dpManager.isAdminActive(myReceiver);
        if (active)
        {
            start.setEnabled(false);
            stop.setEnabled(true);
            setting.setEnabled(true);
        } else
        {
            start.setEnabled(true);
            stop.setEnabled(false);
            setting.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtonStates();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case RESULT_OK:
                Toast.makeText(this, "启动成功", Toast.LENGTH_SHORT).show();
                return;
            default:
                Toast.makeText(this, "取消启动", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
