package com.peini.peini2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private String name;
    private String realName;
    private String mobile;
    private String type;
    private String password;

    private EditText nameText;
    private EditText realNameText;
    private EditText mobileText;
    private RadioGroup typeGroup;
    private EditText passwordText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) typeGroup.findViewById(checkedId);
                String typename = radioButton.getText().toString();
                if(typename.equals("我是老人")){
                    type="old";
                }else{
                    type="young";
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                realName = realNameText.getText().toString();
                mobile = mobileText.getText().toString();
                password = passwordText.getText().toString();

                User registerUser = new User(name,realName,mobile,type,password);

                new registerTask().execute(registerUser);

            }
        });
    }

    private void initViews(){
        nameText = (EditText) findViewById(R.id.registerNameText);
        realNameText = (EditText) findViewById(R.id.registerRealNameText);
        mobileText = (EditText) findViewById(R.id.registerMobileText);
        typeGroup = (RadioGroup) findViewById(R.id.radioGroup);
        passwordText = (EditText) findViewById(R.id.registerPasswordText);
        registerButton = (Button) findViewById(R.id.registerRegister);
        Intent intent = getIntent();
        String loginName = intent.getStringExtra("registerName");
        nameText.setText(loginName);
    }

    public class registerTask extends AsyncTask<User,Void,Integer>{

        @Override
        protected Integer doInBackground(User... params) {

            int lengthOfName = params[0].getName().length();
            int lengthOfRealName = params[0].getRealName().length();
            int lengthOfMobile = params[0].getMobile().length();
            int lengthOfPassword = params[0].getPassword().length();

            int[] length = {lengthOfName,lengthOfRealName,lengthOfMobile,lengthOfPassword};

            int[] upperBound = {12,5,11,15};
            int[] lowerBound = {4,2,11,6};

            for(int i=0;i<4;i++){
                if(length[i]>=lowerBound[i] && length[i]<=upperBound[i]){

                }else {
                    return i+3;
                }
            }

            Boolean result = UserService.register(params[0]);
            if(result){
                return 1;
            }else {
                return 0;
            }

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            switch (integer) {
                case 1:
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "用户名重复", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this, "用户名格式错误", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(RegisterActivity.this, "真实姓名格式错误", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(RegisterActivity.this, "手机格式错误", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(RegisterActivity.this, "密码格式错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}
