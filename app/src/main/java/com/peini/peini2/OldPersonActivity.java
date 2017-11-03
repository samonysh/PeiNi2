package com.peini.peini2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OldPersonActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView realNameView;
    private TextView mobileNameView;

    private Button indexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_person);

        initViews();

        indexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indexIntent = new Intent(OldPersonActivity.this,OldIndexActivity.class);
                startActivity(indexIntent);
                finish();
            }
        });
    }

    private void initViews(){
        nameView = (TextView) findViewById(R.id.personName);
        realNameView = (TextView) findViewById(R.id.personRealName);
        mobileNameView = (TextView) findViewById(R.id.personMobile);

        indexButton = (Button) findViewById(R.id.oldIndexButtonPerson);

        new OldShowPersonData().execute(PersonData.userName);
    }

    public class OldShowPersonData extends AsyncTask<String,Void,User> {
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
