package com.peini.peini2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private String name=null;
    private String password=null;

    private EditText nameText;
    private EditText passwordText;
    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initViews();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                name = nameText.getText().toString();
                registerIntent.putExtra("registerName",name);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                password = passwordText.getText().toString();

                User loginUser = new User(name,password);

                new LoginTask().execute(loginUser);

            }
        });
    }

    private void initViews(){
        nameText = (EditText) findViewById(R.id.loginName);
        passwordText = (EditText) findViewById(R.id.loginPassword);
        registerButton = (Button) findViewById(R.id.loginRegister);
        loginButton = (Button) findViewById(R.id.loginLogin);
    }


    public class LoginTask extends AsyncTask<User,Void,Integer>{

        @Override
        protected Integer doInBackground(User... params) {

            Integer result = UserService.login(params[0]);

            return result;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (integer) {
                case 1:
                    Intent oldIntent = new Intent(LoginActivity.this,OldIndexActivity.class);
                    startActivity(oldIntent);
                    finish();
                    break;
                case 2:
                    Intent youngIntent = new Intent(LoginActivity.this,YoungIndexActivity.class);
                    startActivity(youngIntent);
                    finish();
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "登录错误，请检查用户名与密码", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
