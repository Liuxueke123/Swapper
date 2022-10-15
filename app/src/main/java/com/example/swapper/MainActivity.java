package com.example.swapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.admin.AdminActivity;
import com.example.swapper.user.UserInfoActivity;


public class MainActivity extends AppCompatActivity {
    public static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user_id="2";

        findViewById(R.id.personal_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("UID",user_id); //transfer the user_id for the login user
                startActivity(intent);
            }
        });

        findViewById(R.id.admin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AdminActivity.class);
                intent.putExtra("TYPE","user"); //transfer the user_id for the login user
                startActivity(intent);
            }
        });
    }
}