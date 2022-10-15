package com.example.swapper.user;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swapper.MainActivity;
import com.example.swapper.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ModifyPswdActivity extends AppCompatActivity{
    private EditText old_pswd;
    private EditText new_pswd;
    private EditText confirm_pswd;
    String oldp,newp,confirmp,pswd;
    Handler mhandler;
    private static String DbDriver = "com.mysql.jdbc.Driver";
    private static String Url = "jdbc:mysql://10.28.175.121:3306/swapper?useSSL=false&allowPublicKeyRetrieval=true";
    private static String User = "swapper";
    private static String Password = "swapper";
    Connection conn=null;
    Statement stat=null;
    ResultSet rs=null;
    PreparedStatement stmt=null;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pswd_modify);
        String get_userid = MainActivity.intent.getStringExtra("UID");
        old_pswd= (EditText) findViewById(R.id.opswd_input);
        new_pswd=(EditText) findViewById(R.id.npswd_input);
        confirm_pswd=(EditText) findViewById(R.id.cpswd_input);

        findViewById(R.id.pswd_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ModifyPswdActivity.this,UserInfoActivity.class));
            }
        });

        findViewById(R.id.pswd_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newp=new_pswd.getText().toString();
                confirmp=confirm_pswd.getText().toString();
                oldp=old_pswd.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Class.forName(DbDriver);
                            Log.v(TAG, "加载JDBC驱动成功");
                            conn = DriverManager.getConnection(Url, User, Password);
                            Log.v(TAG, "test");
                            stat = conn.createStatement();
                            String sql = "SELECT * FROM user where user_id='"+get_userid+"'";
                            rs = stat.executeQuery(sql);
                            rs.next();
                            pswd=rs.getString("user_pswd");
                            Log.v(TAG, pswd);
                            Log.v(TAG, oldp);
                            if (oldp.equals(pswd)) {
                                if (newp.equals(confirmp)) {
                                    System.out.println("pswd:"+oldp+" "+newp+" "+confirmp);
                                    String sql2 = "UPDATE user SET user_pswd = ? WHERE user_id = ?";
                                    stmt = conn.prepareStatement(sql2);
                                    stmt.setString(1,newp);
                                    stmt.setInt(2, Integer.parseInt(get_userid));
                                    stmt.executeUpdate();
                                }
                            }
                            conn.close();
                            stmt.close();
                            stat.close();
                            rs.close();

                            finish();
                            startActivity(new Intent(ModifyPswdActivity.this,UserInfoActivity.class));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                if (oldp.equals("")||newp.equals("")||confirmp.equals("")) Toast.makeText(getApplicationContext(), "Please complete the form!", Toast.LENGTH_SHORT).show();
                else if (!oldp.equals(pswd)) Toast.makeText(getApplicationContext(), "Old password wrong!", Toast.LENGTH_SHORT).show();
                else if (!newp.equals(confirmp)) Toast.makeText(getApplicationContext(), "The two new passwords are different!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
