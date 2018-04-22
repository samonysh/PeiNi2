package com.peini.peini2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoungIndexActivity extends AppCompatActivity {

    private RecyclerView youngList;
    private ChatAdapter mChatAdapter;
    private List<ChatListData> mList = new ArrayList<>();

    private RecognizerDialog dialog;
    private SpeechSynthesizer mTts;

    //按钮
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

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnVoice();

            }

        });

    }

    private void initViews(){
        recordButton = (Button) findViewById(R.id.youngRecord);

        youngList = (RecyclerView) findViewById(R.id.youngList);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getBaseContext());
        youngList.setLayoutManager(linearLayoutManager);
        mChatAdapter = new ChatAdapter(getBaseContext(), mList);
        youngList.setAdapter(mChatAdapter);
        //设置sdcard的路径
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";
    }


    //TODO 开始说话：
    private void btnVoice() {
        dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        dialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, getWavFilePath());
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
                            addRightItem(text);
                            String url = "http://op.juhe.cn/robot/index?info=" + text
                                    + "&key=7a48539921338ef90866922b21e25f6d";
                            RxVolley.get(url, new HttpCallback() {
                                @Override
                                public void onSuccess(String t) {
                                    parsingJson(t);
                                }
                            });
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

    private void parsingJson(String t) {
        try {
            JSONObject jsonObhect = new JSONObject(t);
            JSONObject jsonresult = jsonObhect.getJSONObject("result");
            //拿到返回值
            String text = jsonresult.getString("text");
            //拿到机器人的返回值之后添加在left item
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public static boolean isSdcardExit(){
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
    public static String getWavFilePath(){
        String mAudioWavPath = "query.wav";
        if(isSdcardExit()){
            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mAudioWavPath = fileBasePath+"/"+"query.wav";
        }
        return mAudioWavPath;
    }

    private void addRightItem(String s) {
        ChatListData date = new ChatListData();
        date.setType(ChatAdapter.VALUE_RIGHT_TEXT);
        date.setText(s);
        mList.add(date);
        //通知adapter刷新
        mChatAdapter.notifyDataSetChanged();
        //滚动到底部
        youngList.smoothScrollToPosition(youngList.getBottom());
    }
    private void addLeftItem(String s) {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "60");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        startSpeak(s);

        ChatListData date = new ChatListData();
        date.setType(ChatAdapter.VALUE_LEFT_TEXT);
        date.setText(s);
        mList.add(date);
        //通知adapter刷新
        mChatAdapter.notifyDataSetChanged();
        //滚动到底部
        youngList.smoothScrollToPosition(youngList.getBottom());
    }

    private void startSpeak(String text){
        //开始讲话
        mTts.startSpeaking( text, mSynListener );
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}

