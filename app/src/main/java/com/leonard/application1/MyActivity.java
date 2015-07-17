package com.leonard.application1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 表情商店 on 2015/7/17.
 */
public class MyActivity extends Activity {
    private EditText input;
    private Button commit;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);
        input = (EditText) findViewById(R.id.input);
        commit = (Button) findViewById(R.id.commit);
        result = (TextView) findViewById(R.id.result);

        /**
         * 当点击提交按钮后就执行命令行语句
         */
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String command = input.getText().toString().trim();
                if (!command.equals("")) {
                    runCommand(command);
                    input.setText("");
                }
            }
        });
    }

    /**
     * 执行具体的命令
     *
     * @param command 命令语句
     */
    private void runCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            InputStreamReader mReader = new InputStreamReader(process.getInputStream());
            //获得输入数据
            BufferedReader reader = new BufferedReader(mReader);
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            process.waitFor();
            result.setText(buffer.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result.setText("您的权限不足或系统出错");
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
