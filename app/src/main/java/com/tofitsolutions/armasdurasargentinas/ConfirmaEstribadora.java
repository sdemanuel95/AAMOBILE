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
import com.tofitsolutions.armasdurasargentinas.controllers.SubproductoController;
import com.tofitsolutions.armasdurasargentinas.restControllers.DeclaracionImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.IngresoMPImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.ItemImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.MermaImpl;
import com.tofitsolutions.armasdurasargentinas.util.Conexion;

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
    private ItemController itemController = new ItemController();
    private StockController stockController = new StockController();
    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();
    Maquina maquina = null;
    IngresoMP ingresoMP1 = null;
    IngresoMP ingresoMP2 = null;
    Items itemObject = null;
    String usuario;
    String ayudante;
    String item;
    int cantidadAUsar;
    Double kgAProducir;
    String kgTotalItem;
    Items itemADeclarar;

    private DeclaracionImpl declaracionImpl;
    private IngresoMPImpl ingresoMPImpl;
    private ItemImpl itemImpl;
    private MermaImpl mermaImpl;
    SubproductoController subproductoController;

    boolean declaroTodo;
    boolean subproducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_estribadora);
        ingresoMPController = new IngresoMPController();
        subproductoController = new SubproductoController();
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


        //SERVICIOS REST

        declaracionImpl = new DeclaracionImpl();
        mermaImpl = new MermaImpl();
        itemImpl = new ItemImpl();
        ingresoMPImpl = new IngresoMPImpl();


        declaroTodo = intentPrecintos.getBooleanExtra("declaroTodo",false);
        subproducto = intentPrecintos.getBooleanExtra("subproducto",false);
        usuario = intentPrecintos.getStringExtra("usuario");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        maquina = (Maquina)intentPrecintos.getSerializableExtra("maquina");
        ingresoMP1 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP1");
        ingresoMP2 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP2");
        if(ingresoMP2==null){
            ingresoMP2 = new IngresoMP();
        }
        itemObject = (Items) intentPrecintos.getSerializableExtra("itemObject");
        item = intentPrecintos.getStringExtra("item");
        cantidadAUsar = intentPrecintos.getIntExtra("cantidad",0);
        kgAProducir = intentPrecintos.getDoubleExtra("kgAProducir",0);
        itemADeclarar = itemController.getItem(item);

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
                        guardarDeclaracion();
                        
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
                i.putExtra("ingresoMP1",ingresoMP1);
                i.putExtra("ingresoMP2",ingresoMP2);
                i.putExtra("kgAUsarMP1",ingresoMP1.getKgDisponible());
                i.putExtra("kgAUsarMP2",ingresoMP2.getKgDisponible());
                i.putExtra("usuario",usuario);
                i.putExtra("ayudante", ayudante);
                i.putExtra("maquina", maquina);
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


    public void guardarDeclaracion(){


        //INSERT DECLARACIÓN.
        String equipo = maquina.getMarca() + "-" + maquina.getModelo();
        String precintoA = ingresoMP1.getLote() + ingresoMP1.getMaterial() + ingresoMP1.getCantidad();

        String cantidadKGTOTAL = String.valueOf(kgAProducir);
        String cantidadKG = String.valueOf(kgAProducir);

        Declaracion d = new Declaracion(null,null,usuario,ayudante,equipo,precintoA,null,item,String.valueOf(cantidadAUsar),String.valueOf(kgAProducir),String.valueOf(kgAProducir),"0");


        if(declaroTodo){
            subproductoController.nuevoSubproducto(item,subproducto);
        }

        // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO

        //INSERT EN MERMA

        String mermaCalculadaTOTAL = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
        String mermaCalculada = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
        Merma merma1 = new Merma(null,null,ingresoMP1.getReferencia(),ingresoMP1.getMaterial(),ingresoMP1.getDescripcion(),ingresoMP1.getUmb(),ingresoMP1.getCantidad(),ingresoMP1.getLote(),ingresoMP1.getDestinatario(),ingresoMP1.getColada(),ingresoMP1.getPesoPorBalanza(),ingresoMP1.getKgTeorico(),"0",mermaCalculada,"4310960",itemObject.getDiametro());
        mermaImpl.crearMerma(merma1);



        //stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza,Codigo) values (NOW(),'" + ingresoMP1.getReferencia() + "','"+ingresoMP1.getCantidad() + "','" + ingresoMP1.getLote() + "','" + ingresoMP1.getColada() + "','"+ mermaCalculadaTOTAL + "','4310960')");

        String kgdis1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP1.getKgDisponible()) - (Double.parseDouble(cantidadKG) + Double.parseDouble(mermaCalculada))));
        String kgprod1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP1.getKgProd()) + Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/));


        //stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod1 +"', KGDisponible = '" + kgdis1+"' where lote ='" + lote + "' AND material = '" + material + "' And cantidad ='" + cantidadCodBarra + "';");
        ingresoMP1.setKgDisponible(kgdis1);
        ingresoMP1.setKgProd(kgprod1);

        new guardarDeclaracionAT().execute(kgdis1,kgprod1,String.valueOf(ingresoMP1.getId()));

        /*
        boolean actualizoLote = ingresoMPImpl.actualizarIngresoMP(ingresoMP1);
        if(!actualizoLote){
            //No actualizó el lote así que se cancela la declaración!!
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaEstribadora.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            builder.setTitle("Atencion!");
            builder.setMessage("Ocurrió un error al actualizar el precinto, porfavor vuelva a intentarlo.");
            builder.setCancelable(false);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

            return;
        }
        */
        declaracionImpl.crearDeclaracion(d);
        int cantidadDelItem = Integer.parseInt(itemADeclarar.getCantidad());
        int cantidadDecDelItem = Integer.parseInt(itemADeclarar.getCantidadDec());


        //cantidadDelItem = cantidadDelItem - Integer.parseInt(cantidad);
        cantidadDecDelItem = cantidadDecDelItem + cantidadAUsar;

        itemObject.setCantidadDec(String.valueOf(cantidadDecDelItem));
        // stmt.executeUpdate("update items set CantidadDec = '" + cantidadDecDelItem+"' where Codigo ='" + item + "';");


        itemImpl.actualizarItem(itemObject);
        //STOCK NO HACE FALTA
        //ingresoMPController.updatekg(codBarrasA + cantidad);
        //Stock stock = stockController.getStock(ingresoMP1.getMaterial());
        //String stockKGPROD = stock.getKgprod();
        //String stockKGDISP = stock.getKgdisponible();


        //stockKGPROD = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGPROD )+ (Double.parseDouble(cantidadKGTOTAL) /*-Double.parseDouble(mermaCalculada)*/)));
        //stockKGDISP = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGDISP) - ((Double.parseDouble(cantidadKGTOTAL)) + Double.parseDouble(mermaCalculadaTOTAL))));
        //ACTUALIZA EN STOCK
        // stmt.executeUpdate("update stock set KGProd = '" + stockKGPROD +"', KGDisponible = '" + stockKGDISP+"' where CodMat ='" + stock.getCodMat() + "';");





        Intent i = new Intent(ConfirmaEstribadora.this, Estribadora2Activity.class);

        i.putExtra("ingresoMP1",ingresoMP1);
        i.putExtra("kgAUsarMP1",ingresoMP1.getKgDisponible());
        i.putExtra("usuario",usuario);
        i.putExtra("ayudante", ayudante);
        i.putExtra("maquina", maquina);
        finish();
        startActivity(i);

    }

    public static class guardarDeclaracionAT extends AsyncTask<String, Integer, Long> {

        private int progreso = 0;

        @Override
        protected void onPreExecute() {

            //progress.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {


        }

        @Override
        protected Long doInBackground(String... arg0) {


                try {
                Class.forName("com.mysql.jdbc.Driver");
                Conexion conexion = new Conexion();
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                    String a = arg0[0];
                    String b = arg0[1];
                    String c = arg0[2];
                    String query = "update ingresomp set KGDisponible =" + a + " , KGProd = " + b +" where id = "+c+ "; ;";
                    stmt.executeUpdate(query);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }


    }


}

