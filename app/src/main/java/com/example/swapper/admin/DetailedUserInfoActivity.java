package com.example.swapper.admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.R;
import com.example.swapper.user.UserInfoActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetailedUserInfoActivity extends AppCompatActivity {
    private TextView user_name;
    private TextView user_email;
    private TextView user_sex;
    private TextView user_age;
    private TextView user_phone;
    private TextView user_address;
    private TextView user_points;
    private TextView user_ban_post;
    private TextView user_ban_account;
    private EditText change_points_value;
    private String get_userid;
    String name,email,sex,phone,address;
    int age,points;
    boolean ban_post;
    boolean ban_account;
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
        setContentView(R.layout.detailed_user_info);
        get_userid = AdminActivity.intent.getStringExtra("UID");
        user_name=(TextView) findViewById(R.id.name_input);
        user_email=(TextView) findViewById(R.id.email_input);
        user_sex=(TextView) findViewById(R.id.sex_input);
        user_age=(TextView) findViewById(R.id.age_input);
        user_phone=(TextView) findViewById(R.id.phone_input);
        user_address=(TextView) findViewById(R.id.address_input);
        user_points=(TextView) findViewById(R.id.points_input);
        user_ban_post=(TextView) findViewById(R.id.ban_post_input);
        user_ban_account=(TextView) findViewById(R.id.ban_account_input);
        change_points_value=(EditText) findViewById(R.id.change_points_value);

//        Toast.makeText(getApplicationContext(), get_userid, Toast.LENGTH_SHORT).show();

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
                    email = rs.getString("user_email");
                    sex = rs.getString("user_sex");
                    age = rs.getInt("user_age");
                    phone = rs.getString("user_phone");
                    address = rs.getString("user_address");
                    points=rs.getInt("user_points");
                    ban_post=rs.getBoolean("ban_post");
                    ban_account=rs.getBoolean("ban_account");
                    Log.v(TAG,name);

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
                bundle.putString("points",String.valueOf(points));
                bundle.putString("ban_post",ban_post?"Yes":"No");
                bundle.putString("ban_account",ban_account?"Yes":"No");

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
                String get_points = bundle.getString("points");
                String get_ban_post = bundle.getString("ban_post");
                String get_ban_account = bundle.getString("ban_account");

                user_name.setText(get_name);
                user_email.setText(get_email);
                user_sex.setText(get_sex);
                user_age.setText(get_age2);
                user_phone.setText(get_phone);
                user_address.setText(get_address);
                user_points.setText(get_points);
                user_ban_post.setText(get_ban_post);
                user_ban_account.setText(get_ban_account);
            }
        };

        findViewById(R.id.change_points).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                user_points.setText(String.valueOf(points));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            points=Integer.parseInt(change_points_value.getText().toString());

                            String sql2 = "UPDATE user SET user_points = ? WHERE user_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setInt(1,points);
                            stmt.setInt(2, Integer.parseInt(get_userid));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();
                            finish();

                            Intent intent = new Intent(DetailedUserInfoActivity.this, DetailedUserInfoActivity.class);
                            intent.putExtra("UID", get_userid);
                            startActivity(intent);                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        findViewById(R.id.ban_post).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            ban_post=ban_post?false:true;
//                            points=Integer.parseInt(change_points_value.getText().toString());
                            String sql2 = "UPDATE user SET ban_post = ? WHERE user_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setBoolean(1,ban_post);
                            stmt.setInt(2, Integer.parseInt(get_userid));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            finish();

                            Intent intent = new Intent(DetailedUserInfoActivity.this, DetailedUserInfoActivity.class);
                            intent.putExtra("UID", get_userid);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        findViewById(R.id.ban_account).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            ban_account=ban_account?false:true;
                            String sql2 = "UPDATE user SET ban_account = ? WHERE user_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setBoolean(1,ban_account);
                            stmt.setInt(2, Integer.parseInt(get_userid));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            finish();

                            Intent intent = new Intent(DetailedUserInfoActivity.this, DetailedUserInfoActivity.class);
                            intent.putExtra("UID", get_userid);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
