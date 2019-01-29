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

import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.StockController;
import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfirmaLineaCortado extends AppCompatActivity {

    private TextView confirmaUsuario,confirmaAyudante,confirmaEquipo,confirmaLote,confirmaItem,confirmaCantidad;
    private Button bt_okEstribadoraConf;
    private Button bt_principalConfEst;
    private Button bt_cancelConfEst;


    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private Declaracion d;

    public IngresoMP ingreso;
    public Item item;
    public Maquina maquina;
    public String usuario;
    public String ayudante;
    public String cantidad;
    public CodigoMP codigoMP;
    public StockController stockController;
    public IngresoMPController ingresoMPController;
    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_linea_cortado);
        stockController = new StockController();
        confirmaUsuario = (TextView) findViewById(R.id.confirmaUsuario);
        confirmaAyudante = (TextView) findViewById(R.id.confirmaAyudante);
        confirmaEquipo = (TextView) findViewById(R.id.confirmaEquipo);
        confirmaLote = (TextView) findViewById(R.id.confirmaLote);
        confirmaItem = (TextView) findViewById(R.id.confirmaItem);
        confirmaCantidad = (TextView) findViewById(R.id.confirmaCantidad);
        bt_okEstribadoraConf = (Button) findViewById(R.id.bt_okEstribadoraConf);
        bt_principalConfEst = (Button) findViewById(R.id.bt_principalConfEst);
        bt_cancelConfEst = (Button) findViewById(R.id.bt_cancelConfEst);


        //CONTROLLER PARA ACTUALIZAR INGRESO MP

        ingresoMPController = new IngresoMPController();
        //Ingresa info del Activity -> EstribadoraActivity
        Intent intentPrecintos = getIntent();


        ingreso = (IngresoMP) intentPrecintos.getSerializableExtra("ingreso");
        item = (Item) intentPrecintos.getSerializableExtra("item");
        maquina = (Maquina) intentPrecintos.getSerializableExtra("maquina");
        codigoMP = (CodigoMP) intentPrecintos.getSerializableExtra("codigoMP");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        usuario = intentPrecintos.getStringExtra("usuario");
        cantidad = intentPrecintos.getStringExtra("cantidad");


        confirmaUsuario.setText(usuario);
        confirmaAyudante.setText(ayudante);
        confirmaEquipo.setText(maquina.getMarca() + "-" + maquina.getModelo());
        confirmaLote.setText(ingreso.getLote());
        confirmaItem.setText(item.getCodigo());
        confirmaCantidad.setText(cantidad);

        d = new Declaracion(usuario,ayudante,maquina.getMarca() +"-"+maquina.getModelo(),ingreso.getLote(),null,item.getCodigo(),cantidad);


        bt_okEstribadoraConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaLineaCortado.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

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
                Intent i = new Intent(ConfirmaLineaCortado.this, LineaCortado2Activity.class);
                finish();
                startActivity(i);
            }
        });

        bt_principalConfEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmaLineaCortado.this, PrincipalActivity.class);
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
            progress = new ProgressDialog(ConfirmaLineaCortado.this);
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
                    String id = ld.getId();
                    String usuario = ld.getUsuario();
                    String ayudante = ld.getAyudante();
                    String equipo = ld.getEquipo();
                    String precintoA = ld.getPrecintoA();
                    String precintoB = ld.getPrecintoB();
                    String itemString = ld.getItem();
                    String cantidad = ld.getCantidad();

                    Log.d("usuario: ", usuario);
                    progreso++;

                    double kgUnitario = Double.parseDouble(item.getPeso()) / Double.parseDouble(item.getCantidad());
                    double kgAProducir = kgUnitario * Double.parseDouble(cantidad);
                    stmt.executeUpdate("INSERT INTO declaracion (Usuario,Ayudante,Equipo,PrecintoA,PrecintoB,Item,Cantidad,CantidadKG) VALUES ('" + usuario +"','" + ayudante +"','" + equipo + "'," +
                            "'" + precintoA + "','" + precintoB + "','" +  item.getCodigo() +"','" +  cantidad + "','" + kgAProducir + "');" );
                    // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO
                    String mermaCalculada = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
                    String cantidadKG = String.valueOf(kgAProducir);
                    stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza,Codigo) values (NOW(),'" + ingreso.getReferencia() + "','"+ingreso.getCantidad() + "','" + ingreso.getLote() + "','" + ingreso.getColada() + "','"+ mermaCalculada + "','4310960')");

                    String kgdis1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingreso.getKgDisponible()) - (Double.parseDouble(cantidadKG) + Double.parseDouble(mermaCalculada))));
                    String kgprod1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingreso.getKgProd()) + Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/));
                    stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod1 +"', KGDisponible = '" + kgdis1+"' where lote ='" + ingreso.getLote() + "' AND material = '" + ingreso.getMaterial() + "' And cantidad ='" + ingreso.getCantidad() + "';");


                    int cantidadDelItem = Integer.parseInt(item.getCantidad());
                    int cantidadDecDelItem = Integer.parseInt(item.getCantidadDec());
                    cantidadDelItem = cantidadDelItem - Integer.parseInt(cantidad);
                    cantidadDecDelItem = cantidadDecDelItem + Integer.parseInt(cantidad);
                    stmt.executeUpdate("update items set CantidadDec = '" + cantidadDecDelItem+"' where Codigo ='" + item.getCodigo() + "';");


                    //ingresoMPController.updatekg(codBarrasA + cantidad);
                    Stock stock = stockController.getStock(ingreso.getMaterial());
                    String stockKGPROD = stock.getKgprod();
                    String stockKGDISP = stock.getKgdisponible();


                    stockKGPROD = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGPROD )+ (Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/)));
                    stockKGDISP = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGDISP) - ((Double.parseDouble(cantidadKG)) + Double.parseDouble(mermaCalculada))));
                    //ACTUALIZA EN STOCK
                    stmt.executeUpdate("update stock set KGProd = '" + stockKGPROD +"', KGDisponible = '" + stockKGDISP+"' where CodMat ='" + stock.getCodMat() + "';");

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
            Intent i = new Intent(ConfirmaLineaCortado.this, LineaCortado2Activity.class);
            ingreso = ingresoMPController.getMP(ingreso.getLote() + ingreso.getMaterial() + ingreso.getCantidad());
            i.putExtra("ingresoMP",ingreso);
            i.putExtra("kgReal",ingreso.getKgDisponible());
            i.putExtra("codigoMP",codigoMP);
            i.putExtra("maquina",maquina);
            i.putExtra("usuario",usuario);
            i.putExtra("ayudante",ayudante);
            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
}
