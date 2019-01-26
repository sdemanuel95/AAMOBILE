package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfirmaArmados extends AppCompatActivity {

    private TextView usuarioConfEst;
    private TextView AyudanteConfEst;
    private TextView equipoConfEst;
    private TextView preAConfEst;
    private TextView preBConfEst;
    private TextView itemConfEst;
    private TextView cantidadConfEst;
    private Button bt_okEstribadoraConf;
    private Button bt_principalConfEst;
    private Button bt_cancelConfEst;



    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private Declaracion d;



    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();
    String codPreA;
    String codPreB;
    String precintoA;
    String precintoB;
    String usuario;
    String ayudante;
    String maquina;
    String item;
    String cantidad;
    String kgTotalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_armados);

        usuarioConfEst = (TextView) findViewById(R.id.usuarioConfEst);
        AyudanteConfEst = (TextView) findViewById(R.id.AyudanteConfEst);
        equipoConfEst = (TextView) findViewById(R.id.equipoConfEst);
        preAConfEst = (TextView) findViewById(R.id.preAConfEst);
        preBConfEst = (TextView) findViewById(R.id.preBConfEst);
        itemConfEst = (TextView) findViewById(R.id.itemConfEst);
        cantidadConfEst = (TextView) findViewById(R.id.cantidadConfEst);
        bt_okEstribadoraConf = (Button) findViewById(R.id.bt_okEstribadoraConf);
        bt_principalConfEst = (Button) findViewById(R.id.bt_principalConfEst);
        bt_cancelConfEst = (Button) findViewById(R.id.bt_cancelConfEst);

        //Ingresa info del Activity -> EstribadoraActivity
        Intent intentPrecintos = getIntent();
        kgTotalItem = intentPrecintos.getStringExtra("kgTotalItem");
        codPreA = intentPrecintos.getStringExtra("codPreA");
        codPreB = intentPrecintos.getStringExtra("codPreB");
        precintoA = intentPrecintos.getStringExtra("precintoA");
        precintoA = precintoA.substring(0,10);
        precintoB = intentPrecintos.getStringExtra("precintoB");
        usuario = intentPrecintos.getStringExtra("usuario");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        maquina = intentPrecintos.getStringExtra("maquina");
        item = intentPrecintos.getStringExtra("item");
        cantidad = intentPrecintos.getStringExtra("cantidad");

        d = new Declaracion(usuario,ayudante,maquina,precintoA,precintoB,item,cantidad);

        usuarioConfEst.setText(usuario);
        AyudanteConfEst.setText(ayudante);
        equipoConfEst.setText(maquina);
        preAConfEst.setText(precintoA);
        preBConfEst.setText(precintoB);
        itemConfEst.setText(item);
        cantidadConfEst.setText(cantidad);

        bt_okEstribadoraConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaArmados.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

                // set title
                builder.setTitle("Confirmacion");

                // set dialog message
                builder.setMessage("¿Está seguro que desea continuar?");
                builder.setCancelable(false);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new guardarDeclaracion().execute();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // create alert dialog
                AlertDialog dialog = builder.create();

                // show it
                dialog.show();
            }
        });

        // Cancela
        bt_cancelConfEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmaArmados.this, Estribadora2Activity.class);
                finish();
                startActivity(i);
            }
        });

        bt_principalConfEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmaArmados.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    private class guardarDeclaracion extends AsyncTask<Void, Integer, Void> {
        ArrayList<Declaracion> listaDeclaraciones;

        private int progreso = 0;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(ConfirmaArmados.this);
            progress.setMessage("Guardando");
            progress.setTitle("Declaracion");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progress.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progress.incrementProgressBy(1);
            if (progreso == progress.getMax()) {
                progress.dismiss();
            }

            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Conexion conexion = new Conexion();
            listaDeclaraciones = new ArrayList<Declaracion>();
            listaDeclaraciones.add(d);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                progress.setMax(listaDeclaraciones.size());
                for (Declaracion ld : listaDeclaraciones) {
                    long id = ld.getId();
                    String usuario = ld.getUsuario();
                    String ayudante = ld.getAyudante();
                    String equipo = ld.getEquipo();
                    String precintoA = ld.getPrecintoA();
                    String precintoB = ld.getPrecintoB();
                    String item = ld.getItem();
                    String cantidad = ld.getCantidad();

                    Log.d("usuario: ", usuario);
                    progreso++;
                    publishProgress(progreso);
                    stmt.executeUpdate("INSERT INTO declaracion (Usuario,Ayudante,Equipo,PrecintoA,PrecintoB,Item,Cantidad) VALUES ('" + usuario +"','" + ayudante +"','" + equipo + "'," +
                            "'" + precintoA + "','" + precintoB + "','" +  item +"','" +  cantidad +"');" );
                    // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO


                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //listaDeclaracion = listaDeclaraciones;
            Intent i = new Intent(ConfirmaArmados.this, PrincipalActivity.class);
            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
}
