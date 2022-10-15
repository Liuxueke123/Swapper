package com.example.swapper.user;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.MainActivity;
import com.example.swapper.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInfoActivity extends AppCompatActivity {
//    dbConnect mDbConnect=new dbConnect();
    private TextView user_name;
    private TextView user_email;
    private TextView user_sex;
    private TextView user_age;
    private TextView user_phone;
    private TextView user_address;
    String id,name,email,sex,phone,address;
    int age;
    Handler mhandler;
    private static String DbDriver = "com.mysql.jdbc.Driver";
    private static String Url = "jdbc:mysql://10.28.175.121:3306/swapper?useSSL=false&allowPublicKeyRetrieval=true";
    private static String User = "swapper";
    private static String Password = "swapper";
    Connection conn=null;
    Statement stat=null;
    ResultSet rs=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        String get_userid = MainActivity.intent.getStringExtra("UID");
        user_name=(TextView) findViewById(R.id.name_info);
        user_email=(TextView) findViewById(R.id.email_info);
        user_sex=(TextView) findViewById(R.id.sex_info);
        user_age=(TextView) findViewById(R.id.age_info);
        user_phone=(TextView) findViewById(R.id.phone_info);
        user_address=(TextView) findViewById(R.id.address_info);

        Toast.makeText(getApplicationContext(), get_userid, Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Class.forName(DbDriver);
                    Log.v(TAG, "加载JDBC驱动成功");
                    conn = DriverManager.getConnection(Url, User, Password);
                    Log.v(TAG, "test");
                    stat = conn.createStatement();
                    String sql = "SELECT * FROM user where user_id='"+get_userid+"'";
                    Log.v(TAG,sql);
                    rs = stat.executeQuery(sql);
                    rs.next();
                    name = rs.getString("user_name");
                    Log.v(TAG, name);
                    email = rs.getString("user_email");
                    sex = rs.getString("user_sex");
                    age = rs.getInt("user_age");
                    phone = rs.getString("user_phone");
                    address = rs.getString("user_address");
                    Log.v(TAG, "test");
                    rs.close();
                    stat.close();
                    conn.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                Message msg = new Message();
                bundle.putString("name",name);
                bundle.putString("email",email);
                bundle.putString("sex",sex);
                bundle.putInt("age",age);
                bundle.putString("phone",phone);
                bundle.putString("address",address);
                msg.setData(bundle);
                mhandler.sendMessage(msg);


            }
        }).start();

        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String get_name = bundle.getString("name");
                String get_email=bundle.getString("email");
                String get_sex = bundle.getString("sex");
                int get_age = bundle.getInt("age");
                String get_age2 = ""+get_age;
                String get_phone = bundle.getString("phone");
                String get_address = bundle.getString("address");

                user_name.setText(get_name);
                user_email.setText(get_email);
                user_sex.setText(get_sex);
                user_age.setText(get_age2);
                user_phone.setText(get_phone);
                user_address.setText(get_address);
            }
        };


        findViewById(R.id.info_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.modify_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UserInfoActivity.this, ModifyInfoActivity.class));
            }
        });

        findViewById(R.id.modify_pswd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UserInfoActivity.this,ModifyPswdActivity.class));
            }
        });
    }

}
