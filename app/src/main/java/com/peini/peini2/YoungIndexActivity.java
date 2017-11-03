package com.peini.peini2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class YoungIndexActivity extends AppCompatActivity {

    //按钮
    private Button personButton;
    private Button recordButton;

    //状态
    private boolean recordState = false;
    private boolean playState = false;

    //通知
    private static final String LOG_TAG = "RRCORD_TEST";
    //文件路径
    private String fileName = null;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_young_index);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 59f9b4ff");

        initViews();

        personButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoungIndexActivity.this,YoungPersonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnVoice();

            }

        });

    }

    private void initViews(){
        personButton = (Button) findViewById(R.id.youngPersonButtonIndex);
        recordButton = (Button) findViewById(R.id.youngRecord);

        //设置sdcard的路径
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";
    }


    //TODO 开始说话：
    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if(!b) {
                    String text = parseIatResult(recognizerResult.getResultString());
                    // 自动填写地址
                    int res = Match.doMatch(text);

                    switch (res) {
                        case 0:
                            Intent intent = new Intent(YoungIndexActivity.this,YoungPersonActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent pairIntent = new Intent(YoungIndexActivity.this,FormPairActivity.class);
                            startActivity(pairIntent);
                            break;
                        case 2:
                            finish();
                            break;
                        default:
                            break;
                    }

                }
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }


    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}

