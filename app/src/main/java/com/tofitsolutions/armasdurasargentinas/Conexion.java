package com.tofitsolutions.armasdurasargentinas;

import android.util.Log;

import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion  {
    private Connection con;
    public Conexion() {
    }

    public Connection crearConexion() throws SQLException {
        // TEST
        //Connection con = DriverManager.getConnection("jdbc:mysql://b4e9xxkxnpu2v96i.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fq9e54tk8ag0jl2f", "rnahyl78396j7usi", "z4x6xvpkmu82ptrc");

        //PRODUCCION
        //con = DriverManager.getConnection("jdbc:mysql://wvulqmhjj9tbtc1w.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/m04c2f80573m1icb","joifpn3i49i6l0ib", "vwxdfv7no1ascale");

        //LOCAL
        con = DriverManager.getConnection("jdbc:mysql://192.168.1.42:3306/armadurasargentinas","root","root");

        return con;
    }
}
