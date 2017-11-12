package com.peini.peini2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YoungPersonActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView realNameView;
    private TextView mobileNameView;

    private TextView formPair;
    private Button indexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_young_person);
        initViews();

        indexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indexIntent = new Intent(YoungPersonActivity.this,YoungIndexActivity.class);
                startActivity(indexIntent);
                finish();
            }
        });

        formPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pairIntent = new Intent(YoungPersonActivity.this,FormPairActivity.class);
                startActivity(pairIntent);
            }
        });
    }

    private void initViews(){
        nameView = (TextView) findViewById(R.id.personName);
        realNameView = (TextView) findViewById(R.id.personRealName);
        mobileNameView = (TextView) findViewById(R.id.personMobile);
        formPair = (TextView) findViewById(R.id.formPair);

        indexButton = (Button) findViewById(R.id.youngIndexButtonPerson);

        nameView.setText(PersonData.userName);
        realNameView.setText(PersonData.userRealName);
        mobileNameView.setText(PersonData.userMobile);

        // new YoungShowPersonData().execute(PersonData.userName);
    }

    public class YoungShowPersonData extends AsyncTask<String,Void,User> {
        @Override
        protected User doInBackground(String... params) {
            User user = UserService.showPerson(params[0]);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            nameView.setText(user.getName());
            realNameView.setText(user.getRealName());
            mobileNameView.setText(user.getMobile());


        }
    }
}


