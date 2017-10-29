package com.peini.peini2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormPairActivity extends AppCompatActivity {

    private Button formPairButton;
    private EditText formPairNameText;

    private String formPairName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pair);

        initViews();

        formPairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formPairName = formPairNameText.getText().toString();

                new FormPairTask().execute(formPairName,PersonData.userName);

            }
        });
    }

    private void initViews(){
        formPairButton = (Button) findViewById(R.id.formPairButton);
        formPairNameText = (EditText) findViewById(R.id.formPairName);
    }

    public class FormPairTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            return UserService.formPair(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean){
                Toast.makeText(FormPairActivity.this, "配对成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormPairActivity.this,YoungPersonActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(FormPairActivity.this, "配对失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
