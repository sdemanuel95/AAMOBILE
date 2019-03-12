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
import com.tofitsolutions.armasdurasargentinas.restControllers.DeclaracionImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.IngresoMPImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.ItemImpl;
import com.tofitsolutions.armasdurasargentinas.restControllers.MermaImpl;
import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfirmaPasadores extends AppCompatActivity {

    private TextView confirmaUsuario,confirmaAyudante,confirmaEquipo,confirmaLote,confirmaItem,confirmaCantidad;
    private Button bt_okEstribadoraConf;
    private Button bt_principalConfEst;
    private Button bt_cancelConfEst;


    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private Declaracion d;

    public IngresoMP ingreso;
    public Items item;
    public Maquina maquina;
    public String usuario;
    public String ayudante;
    public String cantidad;
    public CodigoMP codigoMP;
    public StockController stockController;
    public IngresoMPController ingresoMPController;
    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();

    public IngresoMPImpl ingresoMPImpl;
    public ItemImpl itemImpl;
    public DeclaracionImpl declaracionImpl;
    public MermaImpl mermaImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_linea_doblado);
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
        item = (Items) intentPrecintos.getSerializableExtra("item");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaPasadores.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

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
                Intent i = new Intent(ConfirmaPasadores.this, Pasadores2Activity.class);
                finish();
                startActivity(i);
            }
        });

        bt_principalConfEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmaPasadores.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }


    public void guardarDeclaracion(){


        //INSERT DECLARACIÓN.
        String equipo = maquina.getMarca() + "-" + maquina.getModelo();
        String precintoA = ingreso.getLote() + ingreso.getMaterial() + ingreso.getCantidad();

        double kgUnitario = Double.parseDouble(item.getPeso()) / Double.parseDouble(item.getCantidad());
        double kgAProducir = kgUnitario * Double.parseDouble(cantidad);
        String cantidadKGTOTAL = String.valueOf(kgAProducir);
        String cantidadKG = String.valueOf(kgAProducir);

        Declaracion d = new Declaracion(null,null,usuario,ayudante,equipo,precintoA,null,item.getCodigo(),String.valueOf(cantidad),String.valueOf(kgAProducir),String.valueOf(kgAProducir),"0");



        // ACA DEBE ACTUALIZAR EN INGRESO MP EL KG DISPONIBLE Y PRODUCIDO

        //UNSERT EN MERMA

        String mermaCalculada = String.valueOf( Double.parseDouble(maquina.getMerma()) * (kgAProducir) / 100);
        Merma merma1 = new Merma(null,null,ingreso.getReferencia(),ingreso.getMaterial(),ingreso.getDescripcion(),ingreso.getUmb(),ingreso.getCantidad(),ingreso.getLote(),ingreso.getDestinatario(),ingreso.getColada(),ingreso.getPesoPorBalanza(),ingreso.getKgTeorico(),"0",mermaCalculada,"4310960",item.getDiametro());
        mermaImpl.crearMerma(merma1);



        //stmt.executeUpdate("insert into merma (Fecha,Referencia,Cantidad,Lote,Colada,PesoPorBalanza,Codigo) values (NOW(),'" + ingresoMP1.getReferencia() + "','"+ingresoMP1.getCantidad() + "','" + ingresoMP1.getLote() + "','" + ingresoMP1.getColada() + "','"+ mermaCalculadaTOTAL + "','4310960')");

        String kgdis1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingreso.getKgDisponible()) - (Double.parseDouble(cantidadKG) + Double.parseDouble(mermaCalculada))));
        String kgprod1 = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(ingreso.getKgProd()) + Double.parseDouble(cantidadKG) /*-Double.parseDouble(mermaCalculada)*/));
        //stmt.executeUpdate("update ingresomp set KGProd = '" + kgprod1 +"', KGDisponible = '" + kgdis1+"' where lote ='" + lote + "' AND material = '" + material + "' And cantidad ='" + cantidadCodBarra + "';");
        ingreso.setKgDisponible(kgdis1);
        ingreso.setKgProd(kgprod1);

        boolean actualizoLote = ingresoMPImpl.actualizarIngresoMP(ingreso);
        if(!actualizoLote){
            //No actualizó el lote así que se cancela la declaración!!
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmaPasadores.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
        declaracionImpl.crearDeclaracion(d);
        int cantidadDelItem = Integer.parseInt(item.getCantidad());
        int cantidadDecDelItem = Integer.parseInt(item.getCantidadDec());


        //cantidadDelItem = cantidadDelItem - Integer.parseInt(cantidad);
        cantidadDecDelItem = cantidadDecDelItem + Integer.parseInt(cantidad);

        item.setCantidadDec(String.valueOf(cantidadDecDelItem));
        // stmt.executeUpdate("update items set CantidadDec = '" + cantidadDecDelItem+"' where Codigo ='" + item + "';");


        itemImpl.actualizarItem(item);
        //STOCK NO HACE FALTA
        //ingresoMPController.updatekg(codBarrasA + cantidad);
        //Stock stock = stockController.getStock(ingresoMP1.getMaterial());
        //String stockKGPROD = stock.getKgprod();
        //String stockKGDISP = stock.getKgdisponible();


        //stockKGPROD = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGPROD )+ (Double.parseDouble(cantidadKGTOTAL) /*-Double.parseDouble(mermaCalculada)*/)));
        //stockKGDISP = String.valueOf(com.tofitsolutions.armasdurasargentinas.util.Util.setearDosDecimales(Double.parseDouble(stockKGDISP) - ((Double.parseDouble(cantidadKGTOTAL)) + Double.parseDouble(mermaCalculadaTOTAL))));
        //ACTUALIZA EN STOCK
        // stmt.executeUpdate("update stock set KGProd = '" + stockKGPROD +"', KGDisponible = '" + stockKGDISP+"' where CodMat ='" + stock.getCodMat() + "';");

        Intent i = new Intent(ConfirmaPasadores.this, Pasadores2Activity.class);
        i.putExtra("ingresoMP",ingreso);
        i.putExtra("kgReal",ingreso.getKgDisponible());
        i.putExtra("codigoMP",codigoMP);
        i.putExtra("maquina",maquina);
        i.putExtra("usuario",usuario);
        i.putExtra("ayudante",ayudante);
        finish();
        startActivity(i);


    }
}
