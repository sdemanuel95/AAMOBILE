package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class LoginInventarioActivity extends AppCompatActivity{

    private EditText et_usuarioInventario, et_passwordInventario;
    private Button bt_loguearme;
    private TextView excepcion;
    private boolean usuarioInventarioCorrecto, passwordInventarioCorrecto;
    private ArrayList<String> usuarios,passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inventario);

        et_usuarioInventario = (EditText) findViewById(R.id.usuario);
        et_passwordInventario = (EditText) findViewById(R.id.contraseña);
        bt_loguearme = (Button) findViewById(R.id.loguearme);
        excepcion = (TextView) findViewById(R.id.excepcionSesionDescarga);
        usuarios= new ArrayList<>();
        passwords= new ArrayList<>();

        new LoginInventarioActivity.MyTask().execute();
        usuarioInventarioCorrecto = false;
        passwordInventarioCorrecto = false;

        bt_loguearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index = 0; index < usuarios.size(); index++) {
                    String u = et_usuarioInventario.getText().toString();
                    String a = et_passwordInventario.getText().toString();
                    if (u.equals(usuarios.get(index))) {
                        usuarioInventarioCorrecto = true;
                        if(a.equals(passwords.get(index))) {
                            passwordInventarioCorrecto = true;
                        }
                    }
                }
                if (!usuarioInventarioCorrecto && !passwordInventarioCorrecto) {
                    et_usuarioInventario.setBackgroundResource(R.drawable.border_error);
                    et_passwordInventario.setBackgroundResource(R.drawable.border_error);
                } else if (!passwordInventarioCorrecto) {
                    et_usuarioInventario.setBackgroundResource(R.drawable.border_correcto);
                    et_passwordInventario.setBackgroundResource(R.drawable.border_error);
                    usuarioInventarioCorrecto = false;
                } else if (!usuarioInventarioCorrecto){
                    et_usuarioInventario.setBackgroundResource(R.drawable.border_error);
                    et_passwordInventario.setBackgroundResource(R.drawable.border_correcto);
                    passwordInventarioCorrecto = false;
                } else {
                    // CREAR NUEVO ACTIVITY
                    Intent i = new Intent(LoginInventarioActivity.this, InventarioActivity.class);
                    i.putExtra("usuario", et_usuarioInventario.getText().toString());
                    i.putExtra("password MOGOLOCI", et_passwordInventario.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> usuariosDB, passwordsDB;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            usuariosDB = new ArrayList<>();
            passwordsDB = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosinventario");
                while (rs.next()) {
                    usuariosDB.add(rs.getString("codigo"));
                    passwordsDB.add(rs.getString("contraseña"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                excepcionBD = e.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            usuarios = usuariosDB;
            passwords = passwordsDB;
            excepcion.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }
}
