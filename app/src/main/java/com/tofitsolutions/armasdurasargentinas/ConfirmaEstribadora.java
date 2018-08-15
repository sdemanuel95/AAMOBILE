package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfirmaEstribadora extends AppCompatActivity {

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
    private IngresoMPController ingresoMPController;

    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private Declaracion d;



    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();
    String codPreA;
    String codPreB;
    String precintoA;
    String codBarrasA;
    String codBarrasB;
    String precintoAcortado;
    String precintoB;
    String usuario;
    String ayudante;
    String maquina;
    String item;
    String cantidad;
    String kgTotalItem;
    int merma;
    String diametro_minimo;
    String diametro_maximo;
    String kgdisponible1;
    String kgdisponible2;
    String kdproducido1;
    String kdproducido2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_estribadora);
        ingresoMPController = new IngresoMPController();
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

        codBarrasA = intentPrecintos.getStringExtra("precintoA");

        codBarrasB = intentPrecintos.getStringExtra("precintoB");


        kgdisponible1 = intentPrecintos.getStringExtra("kgdisponible1");
        kgdisponible2 = intentPrecintos.getStringExtra("kgdisponible2");
        kdproducido1 = intentPrecintos.getStringExtra("kdproducido1");
        kdproducido2 = intentPrecintos.getStringExtra("kdproducido2");

        precintoAcortado = precintoA.substring(0,10);
        precintoB = intentPrecintos.getStringExtra("precintoB");
        usuario = intentPrecintos.getStringExtra("usuario");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        maquina = intentPrecintos.getStringExtra("maquina");
        merma = Integer.valueOf(intentPrecintos.getStringExtra("merma"));
        diametro_minimo = intentPrecintos.getStringExtra("diametro_minimo");
        diametro_maximo = intentPrecintos.getStringExtra("diametro_maximo");
        item = intentPrecintos.getStringExtra("item");
        cantidad = intentPrecintos.getStringExtra("cantidad");

        d = new Declaracion(usuario,ayudante,maquina,precintoAcortado,precintoB,item,cantidad);

        usuarioConfEst.setText(usuario);
        AyudanteConfEst.setText(ayudante);
        equipoConfEst.setText(maquina);
        preAConfEst.setText(precintoAcortado);
        preBConfEst.setText(precintoB);
        itemConfEst.setText(item);
        cantidadConfEst.setText(cantidad);

        bt_okEstribadoraConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaEstribadora.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

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
                Intent i = new Intent(ConfirmaEstribadora.this, Estribadora2Activity.class);
                finish();
                startActivity(i);
            }
        });

        bt_principalConfEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmaEstribadora.this, PrincipalActivity.class);
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
            progress = new ProgressDialog(ConfirmaEstribadora.this);
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
                    String lote = codBarrasA.substring(0,10);
                    String material = codBarrasA.substring(10,20);
                    String cantidadCodBarra = codBarrasA.substring(20,24);
                    String loteB = null;
                    String materialB= null;
                    String cantidadCodBarraB= null;
                    if(codBarrasB != null || codBarrasA != ""){
                        if(codBarrasB.length() == 24){
                            loteB = codBarrasB.substring(0,10);
                            materialB = codBarrasB.substring(10,20);
                            cantidadCodBarraB = codBarrasB.substring(20,24);

                        }
                    }
                    //String loteB = codBarrasB.substring(0,10);
                    //String materialB = codBarrasB.substring(10,20);
                    //String cantidadCodBarraB = codBarrasB.substring(20,24);


                    Log.d("usuario: ", usuario);
                    progreso++;
                    publishProgress(progreso);
                    stmt.executeUpdate("INSERT INTO declaracion (Usuario,Ayudante,Equipo,PrecintoA,PrecintoB,Item,Cantidad) VALUES ('" + usuario +"','" + ayudante +"','" + equipo + "'," +
                            "'" + precintoA + "','" + precintoB + "','" +  item +"','" +  cantidad +"');" );
                    // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO
                    String mermaCalculada = String.valueOf( merma * Double.parseDouble(cantidad) / 100);
                    cantidad = String.valueOf(Double.parseDouble(cantidad) - Double.parseDouble(mermaCalculada));
                    stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza) values (CURRENT_TIME,\"\",1,121,121,'" + mermaCalculada + "')");
                    kgdisponible1 = String.valueOf(Double.parseDouble(kgdisponible1) - Double.parseDouble(cantidad));
                    stmt.executeUpdate("update ingresomp set KGProd = '" + kgdisponible1 +"', KGDisponible = '" + cantidad+"' where lote ='" + lote + "' AND material = '" + material + "' And cantidad ='" + cantidadCodBarra + "';");
                    if(loteB != null || loteB != ""){
                        stmt.executeUpdate("update ingresomp set KGProd = '" + kgdisponible2 +"', KGDisponible = '" + cantidad+"' where lote ='" + loteB + "' AND material = '" + materialB + "' And cantidad ='" + cantidadCodBarraB + "';");

                    }
                    //ingresoMPController.updatekg(codBarrasA + cantidad);
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
            Intent i = new Intent(ConfirmaEstribadora.this, PrincipalActivity.class);
            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
}
