package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class ConfirmaEstribadoraDoble extends AppCompatActivity {

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

    private DeclaracionImpl declaracionImpl;
    private IngresoMPImpl ingresoMPImpl;
    private ItemImpl itemImpl;
    private MermaImpl mermaImpl;
    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private ItemController itemController = new ItemController();
    private StockController stockController = new StockController();
    public SubproductoController subproductoController;
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
    double kgAProducirA;
    double kgAProducirB;
    boolean declaroTodo;
    boolean subproducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_estribadora);
        ingresoMPController = new IngresoMPController();
        subproductoController= new SubproductoController();
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



        //SERVICIOS REST

        declaracionImpl = new DeclaracionImpl();
        mermaImpl = new MermaImpl();
        itemImpl = new ItemImpl();
        ingresoMPImpl = new IngresoMPImpl();
        //Ingresa info del Activity -> EstribadoraActivity
        Intent intentPrecintos = getIntent();
        kgTotalItem = intentPrecintos.getStringExtra("kgTotalItem");

        declaroTodo = intentPrecintos.getBooleanExtra("declaroTodo",false);
        subproducto = intentPrecintos.getBooleanExtra("subproducto",false);
        usuario = intentPrecintos.getStringExtra("usuario");
        ayudante = intentPrecintos.getStringExtra("ayudante");
        maquina = (Maquina)intentPrecintos.getSerializableExtra("maquina");
        ingresoMP1 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP1");
        ingresoMP2 = (IngresoMP) intentPrecintos.getSerializableExtra("ingresoMP2");
        itemObject = (Items) intentPrecintos.getSerializableExtra("itemObject");
        item = intentPrecintos.getStringExtra("item");
        cantidadAUsar = intentPrecintos.getIntExtra("cantidad",0);
        kgAProducir = intentPrecintos.getDoubleExtra("kgAProducir",0);
        kgAProducirA = intentPrecintos.getDoubleExtra("kgAProducirA",0);
        kgAProducirB = intentPrecintos.getDoubleExtra("kgAProducirB",0);
        itemADeclarar = itemController.getItem(item);




        usuarioConfEst.setText(usuario);
        AyudanteConfEst.setText(ayudante);
        equipoConfEst.setText(maquina.getMarca()+"-"+maquina.getModelo());
        preAConfEst.setText(ingresoMP1.getLote());
        preBConfEst.setText(ingresoMP2.getLote());
        itemConfEst.setText(item);
        cantidadConfEst.setText(String.valueOf(cantidadAUsar));

        bt_okEstribadoraConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaEstribadoraDoble.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

                // set title
                builder.setTitle("Confirmacion");

                // set dialog message
                builder.setMessage("¿Está seguro que desea continuar?");
                builder.setCancelable(false);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new guardarDeclaracion().execute();

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
                Intent i = new Intent(ConfirmaEstribadoraDoble.this, Estribadora2DobleActivity.class);

                i.putExtra("ingresoMP1",ingresoMP1);
                i.putExtra("ingresoMP2",ingresoMP2);
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
                Intent i = new Intent(ConfirmaEstribadoraDoble.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

                    public void guardarDeclaracion(){


                    //INSERT DECLARACIÓN.
                    String equipo = maquina.getMarca() + "-" + maquina.getModelo();
                    String precintoA = ingresoMP1.getLote() + ingresoMP1.getMaterial() + ingresoMP1.getCantidad();
                    String precintoB = ingresoMP2.getLote() + ingresoMP2.getMaterial() + ingresoMP2.getCantidad();

                    String cantidadKGTOTAL = String.valueOf(kgAProducir);
                    String cantidadKG = String.valueOf(kgAProducirA);
                    String cantidadKG2 = String.valueOf(kgAProducirB);


                    Declaracion d = new Declaracion(null,null,usuario,ayudante,equipo,precintoA,precintoB,item,String.valueOf(cantidadAUsar),String.valueOf(kgAProducir),String.valueOf(kgAProducirA),String.valueOf(kgAProducirB));

                        if(declaroTodo){
                            subproductoController.nuevoSubproducto(item,subproducto);
                        }

                    // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO

                    //UNSERT EN MERMA

                    String mermaCalculadaTOTAL = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
                    String mermaCalculada = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducirA) / 100);
                    String mermaCalculada2 = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducirB) / 100);
                        Merma merma1 = new Merma(null,null,ingresoMP1.getReferencia(),ingresoMP1.getMaterial(),ingresoMP1.getDescripcion(),ingresoMP1.getUmb(),ingresoMP1.getCantidad(),ingresoMP1.getLote(),ingresoMP1.getDestinatario(),ingresoMP1.getColada(),ingresoMP1.getPesoPorBalanza(),ingresoMP1.getKgTeorico(),"0",mermaCalculada,"4310960",itemObject.getDiametro());
                        Merma merma2 = new Merma(null,null,ingresoMP2.getReferencia(),ingresoMP1.getMaterial(),ingresoMP2.getDescripcion(),ingresoMP2.getUmb(),ingresoMP2.getCantidad(),ingresoMP2.getLote(),ingresoMP2.getDestinatario(),ingresoMP2.getColada(),ingresoMP2.getPesoPorBalanza(),ingresoMP2.getKgTeorico(),"0",mermaCalculada2,"4310960",itemObject.getDiametro());
                        mermaImpl.crearMerma(merma1);
                        mermaImpl.crearMerma(merma2);



                        //stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza,Codigo) values (NOW(),'" + ingresoMP1.getReferencia() + "','"+ingresoMP1.getCantidad() + "','" + ingresoMP1.getLote() + "','" + ingresoMP1.getColada() + "','"+ mermaCalculadaTOTAL + "','4310960')");

                    String kgdis1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP1.getKgDisponible()) - (Double.parseDouble(cantidadKG) + Double.parseDouble(mermaCalculada))));
                    String kgprod1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP1.getKgProd()) + Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/));
                    //stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod1 +"', KGDisponible = '" + kgdis1+"' where lote ='" + lote + "' AND material = '" + material + "' And cantidad ='" + cantidadCodBarra + "';");
                    ingresoMP1.setKgDisponible(kgdis1);
                    ingresoMP1.setKgProd(kgprod1);


                    String kgdis2 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP2.getKgDisponible()) - (Double.parseDouble(cantidadKG2) + Double.parseDouble(mermaCalculada2))));
                    String kgprod2 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingresoMP2.getKgProd()) + Double.parseDouble(cantidadKG2) /*-Double.parseDouble(mermaCalculada)*/));
                    //stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod2 +"', KGDisponible = '" + kgdis2+"' where lote ='" + loteB + "' AND material = '" + materialB + "' And cantidad ='" + cantidadCodBarraB + "';");
                    ingresoMP2.setKgDisponible(kgdis2);
                    ingresoMP2.setKgProd(kgprod2);


                        new guardarDeclaracionAT().execute(kgdis1,kgprod1,String.valueOf(ingresoMP1.getId()));


                        new guardarDeclaracionAT().execute(kgdis2,kgprod2,String.valueOf(ingresoMP2.getId()));

                        declaracionImpl.crearDeclaracion(d);


                    int cantidadDelItem = Integer.parseInt(itemADeclarar.getCantidad());
                    int cantidadDecDelItem = Integer.parseInt(itemADeclarar.getCantidadDec());


                    //cantidadDelItem = cantidadDelItem - Integer.parseInt(cantidad);
                    cantidadDecDelItem = cantidadDecDelItem + cantidadAUsar;

                    itemObject.setCantidadDec(String.valueOf(cantidadDecDelItem));
                   // stmt.executeUpdate("update items set CantidadDec = '" + cantidadDecDelItem+"' where Codigo ='" + item + "';");


                    itemImpl.actualizarItem(itemObject);


                        Intent i = new Intent(ConfirmaEstribadoraDoble.this, Estribadora2DobleActivity.class);

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
