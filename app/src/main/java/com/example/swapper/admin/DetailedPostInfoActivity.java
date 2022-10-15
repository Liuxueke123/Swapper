package com.example.swapper.admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.swapper.R;
import com.example.swapper.user.UserInfoActivity;
import com.example.swapper.admin.AdminActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DetailedPostInfoActivity extends AppCompatActivity {
    private TextView info_input;
    private TextView result_input;
    private TextView audit_input;
    private TextView reply_id_input;
    private TextView post_time_info;
    private String post_id;

    private int post_audit,reply_id;
    private String post_info,post_result;
    private Date post_time;

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
        setContentView(R.layout.detailed_post_info);
        post_id = AdminActivity.intent.getStringExtra("POST_ID");
        info_input=(TextView) findViewById(R.id.info_input);
        result_input=(TextView) findViewById(R.id.result_input);
        audit_input=(TextView) findViewById(R.id.audit_input);
        reply_id_input=(TextView) findViewById(R.id.reply_id_input);
        post_time_info=(TextView) findViewById(R.id.post_time_info);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Class.forName(DbDriver);
                    Log.v(TAG, "加载JDBC驱动成功");
                    conn = DriverManager.getConnection(Url, User, Password);
                    Log.v(TAG, "test");
                    stat = conn.createStatement();
                    String sql = "SELECT * FROM post where post_id='"+post_id+"'";
                    Log.v(TAG,sql);
                    rs = stat.executeQuery(sql);
                    rs.next();
                    post_info = rs.getString("post_info");
                    post_result = rs.getString("post_result");
                    post_audit = rs.getInt("post_audit");
                    reply_id=rs.getInt("reply_id");
                    post_time=rs.getDate("post_time");
                    Log.v(TAG,"---------post_id------------"+post_id);

                    Log.v(TAG,"---------post_info------------"+post_info);

                }catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                Message msg = new Message();
                bundle.putString("post_info",post_info);
                bundle.putString("post_result",post_result);
                bundle.putString("post_audit",String.valueOf(post_audit));
                bundle.putString("reply_id",String.valueOf(reply_id));
                bundle.putString("post_time", String.valueOf(post_time));
                msg.setData(bundle);
                mhandler.sendMessage(msg);
            }
        }).start();

        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String post_info = bundle.getString("post_info");
                String post_result=bundle.getString("post_result");
                String post_audit = bundle.getString("post_audit");
                String reply_id = bundle.getString("reply_id");
                String post_time = bundle.getString("post_time");

                info_input.setText(post_info);
                result_input.setText(post_result);
                audit_input.setText(post_audit);
                reply_id_input.setText(reply_id);
                post_time_info.setText(post_time);

            }
        };


        findViewById(R.id.delete_post).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            String sql2 = "DELETE from post WHERE post_id = "+post_id;
                            stmt = conn.prepareStatement(sql2);
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            finish();
                            Intent intent = new Intent(DetailedPostInfoActivity.this, AdminActivity.class);
                            intent.putExtra("TYPE","post");
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        findViewById(R.id.change_post_status).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v(TAG,"-------------approve------------------");                                    finish();

                            //从EditText中取修改后的值存到数据库中
                            String sql2 = "UPDATE post SET post_result = ? WHERE post_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setString(1,(!post_result.equals("Approve") )?"Approve":"Unapprove");                            stmt.setInt(2, Integer.parseInt(post_id));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            Intent intent = new Intent(DetailedPostInfoActivity.this, DetailedPostInfoActivity.class);
                            intent.putExtra("POST_ID", post_id);
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
