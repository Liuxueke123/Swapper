package com.example.swapper.admin;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.MainActivity;
import com.example.swapper.R;
import com.example.swapper.util.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    public static Intent intent;
    private EditText search_input;
    private TextView result;
    private static String DbDriver = "com.mysql.jdbc.Driver";
    private static String Url = "jdbc:mysql://10.28.175.121:3306/swapper?useSSL=false&allowPublicKeyRetrieval=true";
    private static String User = "swapper";
    private static String Password = "swapper";
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;
    private String  type;
    //user parameter
    private int points;
    private String name, email;
    private boolean ban_post, ban_account;
    //post parameter
    private int post_audit,reply_id;
    private String post_info,post_result;
    private Date post_time;
    //resource
    private int download_num,favorite_num,resource_audit,resource_id,user_id;
    private String resource_class,resource_info,resource_name,resource_result;
    private Date resource_uptime;
    //??resource_file怎么表示？


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        search_input=(EditText) findViewById(R.id.search_input);
        type = getIntent().getStringExtra("TYPE");
        String search_info = getIntent().getStringExtra("SEARCH_INFO");
//        listView = (ListView) findViewById(R.id.listView);
        result= (TextView) findViewById(R.id.result);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName(DbDriver);
                    Log.v(TAG, "加载JDBC驱动成功");
                    conn = DriverManager.getConnection(Url, User, Password);
                    Log.v(TAG, "test");
                    stat = conn.createStatement();
                    StringBuilder result_list= new StringBuilder();
                    String sql="SELECT * FROM "+type;
                    if (Objects.equals(type, "user")){
                        Log.v(TAG, "---------user-----------");
//                        ArrayList<User> list = new ArrayList<>();
//                        System.out.println("用户名" + "\t" + "邮箱" + "\t" + "积分" + "\t" + "禁言"+ "\t" + "封号");
                        if (search_info != null){
                            sql=sql+" where user_name='"+search_info+"'";
                        }
                        Log.v(TAG, sql);
                        rs = stat.executeQuery(sql);
                        while (rs.next()) {
                            name = rs.getString("user_name");
                            email = rs.getString("user_email");
                            points = rs.getInt("user_points");
                            ban_post = rs.getBoolean("ban_post");
                            ban_account = rs.getBoolean("ban_account");
//                            User user = new User(name, email, points, ban_post, ban_account);
//                            list.add(user);
                            result_list.append(name).append(' ');
                        }
                    }
                    else if (Objects.equals(type, "post")){
                        Log.v(TAG, "---------post-----------");
                        if (search_info != null){
                            sql=sql+" where post_info='"+search_info+"'";
                        }
                        Log.v(TAG, sql);
                        rs = stat.executeQuery(sql);
                        while (rs.next()) {
                            post_info = rs.getString("post_info");
                            post_result = rs.getString("post_result");
                            post_audit = rs.getInt("post_audit");
                            reply_id = rs.getInt("reply_id");
                            post_time = rs.getDate("post_time");

                            result_list.append(post_info).append(' ');
                        }
                    }
                    else if (Objects.equals(type, "resource")){
                        Log.v(TAG, "---------resource-----------");
                        if (search_info != null){
                            sql=sql+" where resource_info='"+search_info+"'";
                        }
                        Log.v(TAG, sql);
                        rs = stat.executeQuery(sql);
                        while (rs.next()) {
                            resource_name = rs.getString("resource_name");
                            resource_class = rs.getString("resource_class");
                            resource_info = rs.getString("resource_info");
                            resource_result = rs.getString("resource_result");
                            download_num = rs.getInt("download_num");
                            favorite_num = rs.getInt("favorite_num");
                            resource_audit = rs.getInt("resource_audit");
                            resource_id = rs.getInt("resource_id");
                            user_id = rs.getInt("user_id");
                            resource_uptime = rs.getDate("resource_uptime");
                            result_list.append(resource_name).append(' ');
                        }
                    }
                    Log.v(TAG, "---------sql-----------"+sql);
                    Log.v(TAG, "---------result_list-----------"+result_list);
                    result.setText(result_list.toString());
                    //数据怎么传给前端？？？
//                    show(list);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String search_info= String.valueOf(search_input.getText());
                intent = new Intent(AdminActivity.this, AdminActivity.class);
                intent.putExtra("TYPE",type);
                intent.putExtra("SEARCH_INFO",search_info);
                startActivity(intent);
            }

        });
        findViewById(R.id.view_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, AdminActivity.class);
                intent.putExtra("TYPE","user");
                startActivity(intent);
            }
        });
        findViewById(R.id.view_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, AdminActivity.class);
                intent.putExtra("TYPE","post");
                startActivity(intent);
            }
        });
        findViewById(R.id.view_resource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, AdminActivity.class);
                intent.putExtra("TYPE","resource");
                startActivity(intent);
            }
        });
        findViewById(R.id.detail_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id="2";
                intent = new Intent(AdminActivity.this, DetailedUserInfoActivity.class);
                intent.putExtra("UID", user_id);
                startActivity(intent);
            }
        });
        findViewById(R.id.detail_post_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_id="1";
                intent = new Intent(AdminActivity.this, DetailedPostInfoActivity.class);
                intent.putExtra("POST_ID", post_id);
                startActivity(intent);
            }
        });
        findViewById(R.id.detail_resource_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource_id="1";
                intent = new Intent(AdminActivity.this, DetailedResourceInfoActivity.class);
                intent.putExtra("RESOURCE_ID", resource_id);
                startActivity(intent);
            }
        });
    }

}


