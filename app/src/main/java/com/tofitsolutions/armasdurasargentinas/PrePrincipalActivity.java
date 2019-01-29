package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PrePrincipalActivity extends AppCompatActivity {

    private EditText usuario, ayudante;
    private TextView excepcion;
    private Button ingresar;
    private AlertDialog.Builder builder;
    private boolean usuarioCorrecto, ayudanteCorrecto;
    private ArrayList<String> usuarios, ayudantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preprincipal);
        new MyTask().execute();
        usuarioCorrecto = false;
        ayudanteCorrecto = false;
        usuarios = new ArrayList<>();
        ayudantes = new ArrayList<>();
        usuario = (EditText) findViewById(R.id.et_precintoB);
        ayudante = (EditText) findViewById(R.id.contraseña);
        excepcion = (TextView) findViewById(R.id.excepcion);
        ingresar = (Button) findViewById(R.id.ingresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index = 0; index < usuarios.size(); index++) {
                    String u = usuario.getText().toString();
                    String a = ayudante.getText().toString();
                    if (u.equals(usuarios.get(index))) {
                        usuarioCorrecto = true;
                        if(a.equals(ayudantes.get(index))) {
                            ayudanteCorrecto = true;
                        }
                    }
                }
                if (!usuarioCorrecto && !ayudanteCorrecto) {
                    usuario.setBackgroundResource(R.drawable.border_error);
                    ayudante.setBackgroundResource(R.drawable.border_error);
                } else if (!ayudanteCorrecto) {
                    usuario.setBackgroundResource(R.drawable.border_correcto);
                    ayudante.setBackgroundResource(R.drawable.border_error);
                    usuarioCorrecto = false;
                } else if (!usuarioCorrecto){
                    usuario.setBackgroundResource(R.drawable.border_error);
                    ayudante.setBackgroundResource(R.drawable.border_correcto);
                    ayudanteCorrecto = false;
                } else {
                    // CREAR NUEVO ACTIVITY
                    Intent i = new Intent(PrePrincipalActivity.this, PrincipalActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                    i.putExtra("usuario", usuario.getText().toString());
                    i.putExtra("ayudante", ayudante.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    private void setMensaje(String campo) {
        builder.setMessage("El " + campo + " ingresado no existe, por favor verifique los datos e intente nuevamente PELOTUDO");
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> usuariosBD, ayudantesBD;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            usuariosBD = new ArrayList<>();
            ayudantesBD = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosandroid");
                while (rs.next()) {
                    usuariosBD.add(rs.getString("usuario"));
                    ayudantesBD.add(rs.getString("ayudante"));
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
            usuarios = usuariosBD;
            ayudantes = ayudantesBD;
            excepcion.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }

}
