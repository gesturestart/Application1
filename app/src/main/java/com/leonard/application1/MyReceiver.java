package com.leonard.application1;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 表情商店 on 2015/7/17.
 * 
 */
public class MyReceiver extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "DeviceAdminReceiver：启动");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "DeviceAdminReceiver：是否确定停用";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "DeviceAdminReceiver：停用");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "DeviceAdminReceiver：修改密码");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "DeviceAdminReceiver：密码错误");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "DeviceAdminReceiver：密码正确");
    }
}
