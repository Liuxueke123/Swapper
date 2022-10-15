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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DetailedResourceInfoActivity extends AppCompatActivity {
    private TextView resource_name_input;
    private TextView resource_info_input;
    private TextView resource_class_input;
    private TextView resource_result_input;
    private TextView download_num_input;
    private TextView favorite_num_input;
    private TextView resource_audit_input;
    private TextView resource_id_input;
    private TextView user_id_input;
    private TextView resource_uptime_input;

    private String resource_id;
    private int download_num,favorite_num,resource_audit,user_id;
    private String resource_class,resource_info,resource_name,resource_result;
    private Date resource_uptime;

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
        setContentView(R.layout.detailed_resource_info);
        resource_id = AdminActivity.intent.getStringExtra("RESOURCE_ID");
        resource_name_input=(TextView) findViewById(R.id.resource_name_input);
        resource_info_input=(TextView) findViewById(R.id.resource_info_input);
        resource_class_input=(TextView) findViewById(R.id.resource_class_input);
        resource_result_input=(TextView) findViewById(R.id.resource_result_input);
        download_num_input=(TextView) findViewById(R.id.download_num_input);
        favorite_num_input=(TextView) findViewById(R.id.favorite_num_input);
        resource_audit_input=(TextView) findViewById(R.id.resource_audit_input);
        resource_id_input=(TextView) findViewById(R.id.resource_id_input);
        user_id_input=(TextView) findViewById(R.id.user_id_input);
        resource_uptime_input=(TextView) findViewById(R.id.resource_uptime_input);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Class.forName(DbDriver);
                    Log.v(TAG, "加载JDBC驱动成功");
                    conn = DriverManager.getConnection(Url, User, Password);
                    Log.v(TAG, "test");
                    stat = conn.createStatement();
                    String sql = "SELECT * FROM resource where resource_id='"+resource_id+"'";
                    Log.v(TAG,sql);
                    rs = stat.executeQuery(sql);
                    rs.next();
                    resource_name = rs.getString("resource_name_input");
                    resource_info = rs.getString("resource_info_input");
                    resource_class = rs.getString("resource_class_input");
                    resource_result = rs.getString("resource_result_input");
                    download_num = rs.getInt("download_num_input");
                    favorite_num = rs.getInt("favorite_num_input");
                    resource_audit = rs.getInt("resource_audit_input");
                    resource_id = rs.getString("resource_id_input");
                    user_id = rs.getInt("user_id_input");
                    resource_uptime = rs.getDate("resource_uptime_input");

                }catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                Message msg = new Message();
                bundle.putString("resource_name",resource_name);
                bundle.putString("resource_info",resource_info);
                bundle.putString("resource_class",resource_class);
                bundle.putString("resource_result",resource_result);
                bundle.putString("download_num", String.valueOf(download_num));
                bundle.putString("favorite_num", String.valueOf(favorite_num));
                bundle.putString("resource_audit", String.valueOf(resource_audit));
                bundle.putString("resource_id",resource_id);
                bundle.putString("user_id", String.valueOf(user_id));
                bundle.putString("resource_uptime", String.valueOf(resource_uptime));
                msg.setData(bundle);
                mhandler.sendMessage(msg);
            }
        }).start();

        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String resource_name = bundle.getString("resource_name");
                String resource_info=bundle.getString("resource_info");
                String resource_class = bundle.getString("resource_class");
                String resource_result = bundle.getString("resource_result");
                String download_num = bundle.getString("download_num");
                String favorite_num = bundle.getString("favorite_num");
                String resource_audit=bundle.getString("resource_audit");
                String resource_id = bundle.getString("resource_id");
                String user_id = bundle.getString("user_id");
                String resource_uptime = bundle.getString("resource_uptime");

                resource_name_input.setText(resource_name);
                resource_info_input.setText(resource_info);
                resource_class_input.setText(resource_class);
                resource_result_input.setText(resource_result);
                download_num_input.setText(download_num);
                favorite_num_input.setText(favorite_num);
                resource_audit_input.setText(resource_audit);
                resource_id_input.setText(resource_id);
                user_id_input.setText(user_id);
                resource_uptime_input.setText(resource_uptime);
            }
        };


        findViewById(R.id.delete_resource).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从EditText中取修改后的值存到数据库中
                            String sql2 = "DELETE from resource WHERE resource_id = "+resource_id;
                            stmt = conn.prepareStatement(sql2);
                            stmt.executeUpdate();
                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();
                            finish();
                            Intent intent = new Intent(DetailedResourceInfoActivity.this, AdminActivity.class);
                            intent.putExtra("TYPE","resource");
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        findViewById(R.id.change_resource_status).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.v(TAG,"-------------approve------------------");                                    finish();

                            //从EditText中取修改后的值存到数据库中
                            String sql2 = "UPDATE resource SET resource_result = ? WHERE resource_id = ?";
                            stmt = conn.prepareStatement(sql2);
                            stmt.setString(1,(!resource_result.equals("Approve") )?"Approve":"Unapprove");
                            stmt.setInt(2, Integer.parseInt(resource_id));
                            stmt.executeUpdate();

                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            Intent intent = new Intent(DetailedResourceInfoActivity.this, DetailedResourceInfoActivity.class);
                            intent.putExtra("RESOURCE_ID", resource_id);
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
