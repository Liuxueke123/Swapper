package com.example.swapper.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnect {
    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";
    private final static String Url = "jdbc:mysql://10.28.175.121:3306/swapper?useSSL=false&allowPublicKeyRetrieval=true";
    private final static String User = "swapper";
    private final static String Password = "swapper";
    Connection mConnection=null;
    Statement mStatement=null;

    static {
        try {
            Class.forName(DbDriver);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        try{
            mConnection=DriverManager.getConnection(Url,User,Password);
            System.out.println("conn:"+mConnection);
            android.util.Log.d("conn:", String.valueOf(mConnection));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return mConnection;
    }

    public Statement getStatement(){
        try{
            mStatement=mConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mStatement;
    }
}

