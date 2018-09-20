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
import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;
import com.tofitsolutions.armasdurasargentinas.controllers.StockController;

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
    private ItemController itemController = new ItemController();
    private StockController stockController = new StockController();
    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();
    Maquina maquina = null;
    IngresoMP ingresoMP1 = null;
    IngresoMP ingresoMP2 = null;
    Item itemObject = null;
    String usuario;
    String ayudante;
    String item;
    int cantidadAUsar;
    Double kgAProducir;
    String kgTotalItem;
    Item itemADeclarar;
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


        usuario = intentPrecintos.getStringExtra("usuario");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        maquina = (Maquina)intentPrecintos.getSerializableExtra("maquina");
        ingresoMP1 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP1");
        ingresoMP2 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP2");
        if(ingresoMP2==null){
            ingresoMP2 = new IngresoMP();
        }
        itemObject = (Item) intentPrecintos.getSerializableExtra("itemObject");
        item = intentPrecintos.getStringExtra("item");
        cantidadAUsar = intentPrecintos.getIntExtra("cantidad",0);
        kgAProducir = intentPrecintos.getDoubleExtra("kgAProducir",0);
        itemADeclarar = itemController.getItem(item);
        d = new Declaracion(usuario,ayudante,maquina.getMarca()+"-"+maquina.getModelo(),ingresoMP1.getLote(),null,item,String.valueOf(cantidadAUsar));

        usuarioConfEst.setText(usuario);
        AyudanteConfEst.setText(ayudante);
        equipoConfEst.setText(maquina.getMarca()+"-"+maquina.getModelo());
        preAConfEst.setText(ingresoMP1.getLote());
        preBConfEst.setText("");
        itemConfEst.setText(item);
        cantidadConfEst.setText(String.valueOf(cantidadAUsar));

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
        IngresoMPController ingresoMPController = new IngresoMPController();
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
                    String lote = ingresoMP1.getLote();
                    String material = ingresoMP1.getMaterial();
                    String cantidadCodBarra = ingresoMP1.getCantidad();
                    String loteB = ingresoMP2.getLote();
                    String materialB = ingresoMP2.getMaterial();
                    String cantidadCodBarraB = ingresoMP2.getCantidad();

                    //String loteB = codBarrasB.substring(0,10);
                    //String materialB = codBarrasB.substring(10,20);
                    //String cantidadCodBarraB = codBarrasB.substring(20,24);


                    Log.d("usuario: ", usuario);
                    progreso++;
                    publishProgress(progreso);
                    stmt.executeUpdate("INSERT INTO declaracion (Usuario,Ayudante,Equipo,PrecintoA,PrecintoB,Item,Cantidad) VALUES ('" + usuario +"','" + ayudante +"','" + equipo + "'," +
                            "'" + precintoA + "','" + precintoB + "','" +  item +"','" +  cantidad +"');" );
                    // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO
                    String mermaCalculada = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
                    String cantidadKG = String.valueOf(kgAProducir);
                    stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza) values (NOW(),'" + ingresoMP1.getReferencia() + "','"+ingresoMP1.getCantidad() + "','" + ingresoMP1.getLote() + "','" + ingresoMP1.getColada() + "','"+ mermaCalculada + "')");

                    String kgdis1 = String.valueOf(Double.parseDouble(ingresoMP1.getKgDisponible()) - (Double.parseDouble(cantidadKG) + Double.parseDouble(mermaCalculada)));
                    String kgprod1 = String.valueOf(Double.parseDouble(ingresoMP1.getKgProd()) + Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/);
                    stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod1 +"', KGDisponible = '" + kgdis1+"' where lote ='" + lote + "' AND material = '" + material + "' And cantidad ='" + cantidadCodBarra + "';");


                    int cantidadDelItem = Integer.parseInt(itemADeclarar.getCantidad());
                    int cantidadDecDelItem = Integer.parseInt(itemADeclarar.getCantidadDec());
                    cantidadDelItem = cantidadDelItem - Integer.parseInt(cantidad);
                    cantidadDecDelItem = cantidadDecDelItem + Integer.parseInt(cantidad);
                    stmt.executeUpdate("update items set CantidadDec = '" + cantidadDecDelItem+"' where Codigo ='" + item + "';");

                    if(loteB != null || loteB != ""){
                        //stmt.executeUpdate("update ingresomp set KGProd = '" + ingresoMP2.getKgDisponible() +"', KGDisponible = '" + cantidad+"' where lote ='" + loteB + "' AND material = '" + materialB + "' And cantidad ='" + cantidadCodBarraB + "';");
                    }
                    //ingresoMPController.updatekg(codBarrasA + cantidad);
                    Stock stock = stockController.getStock(ingresoMP1.getMaterial());
                    String stockKGPROD = stock.getKgprod();
                    String stockKGDISP = stock.getKgdisponible();


                    stockKGPROD = String.valueOf(Double.parseDouble(stockKGPROD )+ (Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/));
                    stockKGDISP = String.valueOf(Double.parseDouble(stockKGDISP) - ((Double.parseDouble(cantidadKG)) + Double.parseDouble(mermaCalculada)));
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

            Intent i = new Intent(ConfirmaEstribadora.this, Estribadora2Activity.class);
            ingresoMP1 = ingresoMPController.getMP(ingresoMP1.getLote() + ingresoMP1.getMaterial() + ingresoMP1.getCantidad());
            ingresoMP2 = ingresoMPController.getMP(ingresoMP2.getLote() + ingresoMP2.getMaterial() + ingresoMP2.getCantidad());
            i.putExtra("ingresoMP1",ingresoMP1);
            i.putExtra("ingresoMP2",ingresoMP2);
            i.putExtra("usuario",usuario);
            i.putExtra("ayudante", ayudante);
            i.putExtra("maquina", maquina);

            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
}
