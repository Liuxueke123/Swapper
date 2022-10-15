package com.example.swapper.user;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.MainActivity;
import com.example.swapper.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyInfoActivity extends AppCompatActivity{
    private EditText user_name;
    private EditText user_email;
    private EditText user_sex;
    private EditText user_age;
    private EditText user_phone;
    private EditText user_address;
    String name,email,sex,phone,address;
    int age;
    Handler mhandler;
    private static String DbDriver = "com.mysql.jdbc.Driver";
    private static String Url = "jdbc:mysql://10.28.175.121:3306/swapper?useSSL=false&allowPublicKeyRetrieval=true";
    private static String User = "swapper";
    private static String Password = "swapper";
    Connection conn=null;
    Statement stat=null;
    ResultSet rs=null;
    PreparedStatement stmt=null;
    UserInfoActivity t;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_modify);
        String get_userid = MainActivity.intent.getStringExtra("UID");
        user_name=(EditText) findViewById(R.id.name_input);
        user_email=(EditText) findViewById(R.id.email_input);
        user_sex=(EditText) findViewById(R.id.sex_input);
        user_age=(EditText) findViewById(R.id.age_input);
        user_phone=(EditText) findViewById(R.id.phone_input);
        user_address=(EditText) findViewById(R.id.address_input);

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



        findViewById(R.id.modify).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            name = user_name.getText().toString();
                            email=user_email.getText().toString();
                            sex=user_sex.getText().toString();
                            age=Integer.parseInt(user_age.getText().toString());
                            phone=user_phone.getText().toString();
                            address=user_address.getText().toString();
                            System.out.println("change:"+name+email+sex+age+phone+address);
                            String sql2 = "UPDATE user SET user_name = ?,user_email=?,user_sex = ?,user_age = ?,user_phone = ?,user_address = ? WHERE user_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setString(1,name);
                            stmt.setString(2,email);
                            stmt.setString(3,sex);
                            stmt.setInt(4,age);
                            stmt.setString(5,phone);
                            stmt.setString(6,address);
                            stmt.setInt(7, Integer.parseInt(get_userid));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            finish();
                            startActivity(new Intent(ModifyInfoActivity.this,UserInfoActivity.class));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ModifyInfoActivity.this,UserInfoActivity.class));
            }
        });
    }

}
