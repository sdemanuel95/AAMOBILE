package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.DeclaracionController;
import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Estribadora2Activity extends AppCompatActivity {

    private TextView tv_precintoA, tv_precintoB, tv_usuarioEA2, tv_ayudanteEA2, tv_maquinaEA2, tv_cantidad1KGEA2, tv_cantidad2KGEA2, tv_cantPosible, tv_pendiente;
    private EditText et_ItemEstribadora2;
    private Button bt_teclado, bt_okEstribadora2, bt_principalEstribadora2, bt_cancelEstribadora2;
    //private ArrayList<IngresoMP> materiasPrima;

    private ArrayList<CodigoMP> listaCodigosMP;
    private ArrayList<Item> listaDeItems;
    private ArrayList<Declaracion> listaDeclaracion;
    private int pesoItem;
    private double kgTotalItem;
    private  String cantidad;
    private String item;
    private String pesoPrecintoTotal;
    private ProgressDialog progressI;
    private ProgressDialog progresso;
    private String codPreA;
    private String codPreB;
    private String kdDisponible1;
    private String kdDisponible2;
    private String kdproducido1;
    private String kdproducido2;
    DeclaracionController declaracionController;
    ItemController itemController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estribadora2);
        itemController = new ItemController();
        declaracionController = new DeclaracionController();
        tv_precintoA = (TextView) findViewById(R.id.textView_PrecintoA);
        tv_precintoB = (TextView) findViewById(R.id.textView_PrecintoB);
        tv_usuarioEA2 = (TextView) findViewById(R.id.tv_usuarioEA2);
        tv_ayudanteEA2 = (TextView) findViewById(R.id.tv_ayudanteEA2);
        tv_maquinaEA2 = (TextView) findViewById(R.id.tv_maquinaEA2);
        tv_cantidad1KGEA2 = (TextView) findViewById(R.id.tv_cantidad1KGEA2);
        tv_cantidad2KGEA2 = (TextView) findViewById(R.id.tv_cantidad2KGEA2);
        tv_cantPosible = (TextView) findViewById(R.id.tv_cantPosible);
        tv_pendiente= (TextView) findViewById(R.id.tv_pendiente);
        bt_teclado = (Button) findViewById(R.id.bt_teclado);
        bt_okEstribadora2 = (Button) findViewById(R.id.bt_okEstribadora2);
        bt_principalEstribadora2 = (Button) findViewById(R.id.bt_principalEstribadora2);
        bt_cancelEstribadora2 = (Button) findViewById(R.id.bt_cancelEstribadora2);



        listaCodigosMP = new ArrayList<CodigoMP>();

        et_ItemEstribadora2 = (EditText)findViewById(R.id.et_ItemEstribadora2);
        et_ItemEstribadora2.setInputType(InputType.TYPE_NULL);

        //Ingresa info del Activity -> EstribadoraActivity
        Intent intentPrecintos = getIntent();

        codPreA = intentPrecintos.getStringExtra("codPreA");
        codPreB = intentPrecintos.getStringExtra("codPreB");

        //final String codPreB = intentPrecintos.getStringExtra("codPreB");
        final String precintoA = intentPrecintos.getStringExtra("precintoA");
        final String precintoB = intentPrecintos.getStringExtra("precintoB");
        final String usuario = intentPrecintos.getStringExtra("usuario");
        final String ayudante = intentPrecintos.getStringExtra("ayudante");
        final String maquina = intentPrecintos.getStringExtra("maquina");
        final int diametroMin = Integer.parseInt(intentPrecintos.getStringExtra("diametroMin"));
        final int diametroMax= Integer.parseInt(intentPrecintos.getStringExtra("diametroMax"));
        final String merma = intentPrecintos.getStringExtra("merma");
        final String kgPrecintoA = intentPrecintos.getStringExtra("kgPrecintoA");
        final String kgPrecintoB = intentPrecintos.getStringExtra("kgPrecintoB");
        final String kgdisponible1= intentPrecintos.getStringExtra("kgdisponible1");
        final String kgdisponible2= intentPrecintos.getStringExtra("kgdisponible2");
        final String kdproducido1 = intentPrecintos.getStringExtra("kdproducido1");
        final String kgproducido2 = intentPrecintos.getStringExtra("kdproducido2");
        pesoItem = 0;
        cantidad = null;
        item = "";

        if(!precintoB.isEmpty()) {
            pesoPrecintoTotal = Integer.toString(Integer.parseInt(kgPrecintoA.substring(0, 4)) + Integer.parseInt(kgPrecintoB.substring(0, 4)));
        }
        else {
            pesoPrecintoTotal = Integer.toString(Integer.parseInt(kgPrecintoA.substring(0, 4)));
        }

        tv_precintoA.setText(precintoA.substring(0,10));
        if(precintoB.length() == 24){
            tv_precintoB.setText(precintoB.substring(0,10));

        }
        tv_usuarioEA2.setText(usuario);
        tv_ayudanteEA2.setText(ayudante);
        tv_maquinaEA2.setText(maquina);
        tv_cantidad1KGEA2.setText(kgPrecintoA);
        tv_cantidad2KGEA2.setText(kgPrecintoB);

        bt_teclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_ItemEstribadora2.setInputType(InputType.TYPE_CLASS_TEXT);
                et_ItemEstribadora2.requestFocus();
            }
        });


        et_ItemEstribadora2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String nro = s.toString();

                String itemPendiente = "none";
                String cantPosible = "none";


                if (nro.length()==11) {
                    boolean existe = declaracionController.existe(et_ItemEstribadora2.getText().toString());
                    Log.i("Existe","" + existe);
                    if (declaracionController.existe(et_ItemEstribadora2.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El item ingresado ya encuentra declarado.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        et_ItemEstribadora2.setText("");
                        return;
                    }
                    Item itemTemp = itemController.getItem(et_ItemEstribadora2.getText().toString());
                    if(itemTemp==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El item no existe en la base de datos.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        et_ItemEstribadora2.setText("");
                        return;
                    }
                    if(Integer.parseInt(itemTemp.getDiametro()) < diametroMin || Integer.parseInt(itemTemp.getDiametro()) > diametroMax){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El diametro del item no corresponde con la máquina.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        et_ItemEstribadora2.setText("");
                        return;
                    }

                    // Se actualizan los ET de Cantidad Posible e Item Pendientes
                    item = et_ItemEstribadora2.getText().toString();
                    Item i = itemController.getItem(item);
                    cantidad = (i.getCantidad());
                    tv_cantidad1KGEA2.setText(kgPrecintoA);

                    tv_cantPosible.setText("CP: " + calcularPosible((Double.parseDouble(kgPrecintoA)),Double.parseDouble(i.getPeso())
                            ,Double.parseDouble(cantidad)));
                    tv_pendiente.setText("P: " + "0");

                    /*
                    for (int i = 0; i < listaDeItems.size(); i++) {
                        String codigo = listaDeItems.get(i).getCodigo();
                        cantidad = Integer.parseInt(listaDeItems.get(i).getCantidad());
                        int cantidadDec = Integer.parseInt(listaDeItems.get(i).getCantidadDec());
                        String peso = listaDeItems.get(i).getPeso();
                        if (codigo.equals(item)) {
                            itemPendiente = Integer.toString(cantidad - cantidadDec);
                            pesoItem = Integer.parseInt(peso);
                            break;
                        } else {
                            // Agregar Alert Dialog con el siguiente mensaje: "Error: El Item no existe."
                            itemPendiente = "no";
                        }
                    }
                    int a = Integer.parseInt(pesoPrecintoTotal);
                    int b = pesoItem;
                    int resta = a - b;
                    Log.i("resta", resta + "");
                    if (resta >= 0) {
                        cantPosible = itemPendiente;
                        itemPendiente = "0";
                        Log.i("cantPosible", cantPosible);
                    }
                    tv_cantPosible.setText("CP: " + cantPosible);
                    tv_pendiente.setText("P: " + itemPendiente);


                    // Se valida que el item no este declarado.
                    boolean declarado = false;
                    for (Declaracion dec : listaDeclaracion) {
                        String itemDec = dec.getItem();
                        Log.d("ItemDec = " + itemDec, " ItemET = " + item);
                        if (itemDec.equals(item)) {

                            declarado = true;

                        }
                    }
                    if(declarado == true){
                        String mensaje = "Error: El item "+ item +" ya se encuentra declarado.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        et_ItemEstribadora2.setText("");
                    } else {
                        String mensaje = "Item cargado correctamente.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }

                    */

                }
            }
        });

        bt_okEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_ItemEstribadora2 == null || et_ItemEstribadora2.length() != 11){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El item insertado no corresponde.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                //TODO
                //if(validarItem()){
                    Intent i = new Intent(Estribadora2Activity.this, ConfirmaEstribadora.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);
                    i.putExtra("maquina", maquina);
                    i.putExtra("diametroMin", diametroMin);
                    i.putExtra("diametroMax", diametroMax);
                    i.putExtra("merma", merma);
                    i.putExtra("precintoA", precintoA);
                    i.putExtra("kgdisponible1", kgdisponible1);
                    i.putExtra("kgdisponible2", kgdisponible2);
                    i.putExtra("precintoB", precintoB);
                    i.putExtra("item", item);
                    i.putExtra("cantidad", cantidad);
                    i.putExtra("codPreA", codPreA);
                    i.putExtra("codPreB", codPreB);
                    i.putExtra("kgTotalItem", String.valueOf(kgTotalItem));
                    finish();
                    startActivity(i);
                //}
                /*else{
                    String mensaje = "Error: El código o el diametro del item no se corresponden.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }*/
            }

        });

        // Cancelar
        bt_cancelEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2Activity.this, EstribadoraActivity.class);
                finish();
                startActivity(i);
            }
        });

        // Menú principal
        bt_principalEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2Activity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    private boolean validarItem(){
        boolean validacion = false;
        // Datos del codigoMP
        String tipoMaterial = ""; // ADN, ATR500, ADNS, AL220, ESPECIAL
        String familia = "";
        boolean val = false;

        // Busca el codigo y trae el tipo de material y la familia
        for(CodigoMP codigo : listaCodigosMP){
            String tM = codigo.getTipoMaterial();
            String f = codigo.getFamilia();
            String cod = codigo.getCodSap();
            if (codPreA.equals(cod)) {
                val = true;
                tipoMaterial = tM;
                familia = f;

            }
        }
        if(!val){
            String mensaje = "Error: El codigo no se corresponde con el precinto";
            Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            msjToast.show();
        }

        // Datos Item
        String acero = ""; //ADN 420, ADN420S, ANGULOS, AL220, ALAMBRES, CLAVOS
        String diametro = "";
        // Busca el item y trae el acero y el diametro
        for (int i = 0; i<=listaDeItems.size()-1; i++) {
            String c = listaDeItems.get(i).getCodigo();
            String a = listaDeItems.get(i).getAcero();
            String d = listaDeItems.get(i).getDiametro();
            if (c.equals(item)) {
                acero = a;
                diametro = d;
            }
        }

        // Validacion
        // codigomp ADN, ATR500, ADNS, AL220, ESPECIAL
        //item : ADN420, ADN420S, ANGULOS, AL220, ALAMBRES, CLAVOS

        if (diametro.equals(familia)) {
            if (acero.equals("ADN420") && tipoMaterial.equals("ADN") || tipoMaterial.equals("ADNS")){
                validacion = true;
            }
            else if((acero.equals("ADN420S") && tipoMaterial.equals("ADNS"))){
                validacion = true;
            }
            else{
                String mensaje = "Error: El código del item no se corresponde.";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();
            }
        }
        else {
            String mensaje = "Error: El diametro del item no se corresponde con el diametro del lote.";
            Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            msjToast.show();
        }
        Log.d("diametro : ", diametro);
        Log.d("familia : ", familia);
        return validacion;
    }

    private class traerItems extends AsyncTask<Void, Integer, Void> {
        private ArrayList<Item> listaItems;
        private int progresoItem = 0;

        @Override
        protected void onPreExecute() {
            progressI = new ProgressDialog(Estribadora2Activity.this);
            //progress.setMax(100);
            progressI.setMessage("Cargando");
            progressI.setTitle("Items");
            progressI.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressI.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressI.incrementProgressBy(1);
            if (progresoItem == progressI.getMax()) {
                progressI.dismiss();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaItems = new ArrayList<Item>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rowItemsCompleto = stmt.executeQuery("SELECT * FROM items");

                rowItemsCompleto.last(); //me voy al último
                int sizeRS = rowItemsCompleto.getRow(); //pillo el tamaño
                rowItemsCompleto.beforeFirst(); // lo dejo donde estaba para tratarlo

                progressI.setMax(sizeRS);
                while (rowItemsCompleto.next()) {
                    int id = rowItemsCompleto.getInt("ID");
                    int idpedido = rowItemsCompleto.getInt("IDpedido");
                    int item = rowItemsCompleto.getInt("Item");
                    String posicion = rowItemsCompleto.getString("Posicion");
                    String acero = rowItemsCompleto.getString("Acero");
                    String material = rowItemsCompleto.getString("Material");
                    String diametro = rowItemsCompleto.getString("Diametro");
                    String cantidad = rowItemsCompleto.getString("Cantidad");
                    String cantidadDec = rowItemsCompleto.getString("CantidadDec");
                    String formato = rowItemsCompleto.getString("Formato");
                    String dibujo = rowItemsCompleto.getString("Dibujo");
                    String a = rowItemsCompleto.getString("A");
                    String b = rowItemsCompleto.getString("B");
                    String c = rowItemsCompleto.getString("C");
                    String d = rowItemsCompleto.getString("D");
                    String e = rowItemsCompleto.getString("E");
                    String f = rowItemsCompleto.getString("F");
                    String g = rowItemsCompleto.getString("G");
                    String h = rowItemsCompleto.getString("H");
                    String h1 = rowItemsCompleto.getString("H1");
                    String h2 = rowItemsCompleto.getString("H2");
                    String lparcial = rowItemsCompleto.getString("LParcial");
                    String ltotal = rowItemsCompleto.getString("LTotal");
                    String lcortar = rowItemsCompleto.getString("LCortar");
                    String peso = rowItemsCompleto.getString("Peso");
                    String observaciones = rowItemsCompleto.getString("Observaciones");
                    String estructura = rowItemsCompleto.getString("Estructura");
                    String codigo = rowItemsCompleto.getString("Codigo");
                    listaItems.add(new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, cantidadDec, formato, dibujo, a, b, c, d, e, f, g, h, h1, h2, lparcial, ltotal, lcortar, peso, observaciones, codigo, estructura));
                    progresoItem++;
                    publishProgress(progresoItem);
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
            listaDeItems = listaItems;
            super.onPostExecute(aVoid);
        }
    }

    //NUEVO ASINCSTASK PARA VALIDAR SI UN ITEM YA ESTÁ DECLARADO
    private class validarItemDeclarado extends AsyncTask<Void, Integer, Void> {
        private ArrayList<Declaracion> listaDeclaraciones;
        private int progresoDec = 0;

        @Override
        protected void onPreExecute() {
            progresso = new ProgressDialog(Estribadora2Activity.this);
            //progress.setMax(100);
            progresso.setMessage("Cargando");
            progresso.setTitle("Declaracion");
            progresso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progresso.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progresso.incrementProgressBy(1);
            if (progresoDec == progresso.getMax()) {
                Log.d("Termino de ejecutar", "el asyncktask");
                progresso.dismiss();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaDeclaraciones = new ArrayList<Declaracion>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rowDeclaracionCompleto = stmt.executeQuery("SELECT * FROM declaracion");

                rowDeclaracionCompleto.last(); //me voy al último
                int sizeRS = rowDeclaracionCompleto.getRow(); //pillo el tamaño
                rowDeclaracionCompleto.beforeFirst(); // lo dejo donde estaba para tratarlo

                progresso.setMax(sizeRS);
                while (rowDeclaracionCompleto.next()) {
                    int id = rowDeclaracionCompleto.getInt("ID");
                    String fecha = rowDeclaracionCompleto.getString("Fecha");
                    String usuario = rowDeclaracionCompleto.getString("Usuario");
                    String ayudante = rowDeclaracionCompleto.getString("Ayudante");
                    String equipo = rowDeclaracionCompleto.getString("Equipo");
                    String precintoA = rowDeclaracionCompleto.getString("PrecintoA");
                    String precintoB = rowDeclaracionCompleto.getString("PrecintoB");
                    String item = rowDeclaracionCompleto.getString("Item");
                    String cantidad = rowDeclaracionCompleto.getString("Cantidad");
                    listaDeclaraciones.add(new Declaracion(id, fecha, usuario, ayudante, equipo, precintoA, precintoB, item, cantidad));
                    progresoDec++;
                    publishProgress(progresoDec);
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
            Declaracion d;
            listaDeclaracion = listaDeclaraciones;
            Log.d("declarado", " ok");

            //declarado = true;

            super.onPostExecute(aVoid);
        }
    }

    private class traerCodigosMP extends AsyncTask<Void, Integer, Void> {
        private ArrayList<CodigoMP> listaCodigos;
        private int progresoItem = 0;

        @Override
        protected void onPreExecute() {
            progressI = new ProgressDialog(Estribadora2Activity.this);
            //progress.setMax(100);
            progressI.setMessage("Cargando");
            progressI.setTitle("Materias Primas");
            progressI.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressI.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressI.incrementProgressBy(1);
            if (progresoItem == progressI.getMax()) {
                progressI.dismiss();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaCodigos = new ArrayList<CodigoMP>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM codigomp");

                rs.last(); //me voy al último
                int sizeRS = rs.getRow(); //pillo el tamaño
                rs.beforeFirst(); // lo dejo donde estaba para tratarlo

                progressI.setMax(sizeRS);
                while (rs.next()) {

                    long id = rs.getInt("ID");
                    //String fecha = rs.getString("Fecha");
                    String codSap = rs.getString("CodSap");
                    String familia = rs.getString("Familia");
                    String descripcion = rs.getString("Descripcion");
                    String tipoMaterial = rs.getString("TipoMaterial");

                    listaCodigos.add(new CodigoMP(id, codSap,familia,descripcion,tipoMaterial));

                    progresoItem++;
                    publishProgress(progresoItem);
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
            listaCodigosMP = listaCodigos;
            super.onPostExecute(aVoid);
        }
    }

    //NUEVO ASYNTASK PARA VALIDAR SOLO EL ITEM PUESTO.
    public class traerUnItem extends AsyncTask<Void, Integer, Void> {
        private ArrayList<Item> listaItems;
        private int progresoItem = 0;

        @Override
        protected void onPreExecute() {
            progressI = new ProgressDialog(Estribadora2Activity.this);
            //progress.setMax(100);
            progressI.setMessage("Validando");
            progressI.setTitle("Item");
            progressI.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressI.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressI.incrementProgressBy(1);
            if (progresoItem == progressI.getMax()) {
                progressI.dismiss();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaItems = new ArrayList<Item>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rowItemsCompleto = stmt.executeQuery("SELECT * FROM items where Codigo = '" + et_ItemEstribadora2.getText().toString() + "' limit 1;");

                rowItemsCompleto.last(); //me voy al último
                int sizeRS = rowItemsCompleto.getRow(); //pillo el tamaño
                rowItemsCompleto.beforeFirst(); // lo dejo donde estaba para tratarlo

                progressI.setMax(sizeRS == 0 ? 0 : 0);
                while (rowItemsCompleto.next()) {
                    int id = rowItemsCompleto.getInt("ID");
                    int idpedido = rowItemsCompleto.getInt("IDpedido");
                    int item = rowItemsCompleto.getInt("Item");
                    String posicion = rowItemsCompleto.getString("Posicion");
                    String acero = rowItemsCompleto.getString("Acero");
                    String material = rowItemsCompleto.getString("Material");
                    String diametro = rowItemsCompleto.getString("Diametro");
                    String cantidad = rowItemsCompleto.getString("Cantidad");
                    String cantidadDec = rowItemsCompleto.getString("CantidadDec");
                    String formato = rowItemsCompleto.getString("Formato");
                    String dibujo = rowItemsCompleto.getString("Dibujo");
                    String a = rowItemsCompleto.getString("A");
                    String b = rowItemsCompleto.getString("B");
                    String c = rowItemsCompleto.getString("C");
                    String d = rowItemsCompleto.getString("D");
                    String e = rowItemsCompleto.getString("E");
                    String f = rowItemsCompleto.getString("F");
                    String g = rowItemsCompleto.getString("G");
                    String h = rowItemsCompleto.getString("H");
                    String h1 = rowItemsCompleto.getString("H1");
                    String h2 = rowItemsCompleto.getString("H2");
                    String lparcial = rowItemsCompleto.getString("LParcial");
                    String ltotal = rowItemsCompleto.getString("LTotal");
                    String lcortar = rowItemsCompleto.getString("LCortar");
                    String peso = rowItemsCompleto.getString("Peso");
                    String observaciones = rowItemsCompleto.getString("Observaciones");
                    String estructura = rowItemsCompleto.getString("Estructura");
                    String codigo = rowItemsCompleto.getString("Codigo");
                    listaItems.add(new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, cantidadDec, formato, dibujo, a, b, c, d, e, f, g, h, h1, h2, lparcial, ltotal, lcortar, peso, observaciones, codigo, estructura));
                    progresoItem++;
                    //publishProgress(progresoItem);
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
            listaDeItems = listaItems;
            if(listaItems.isEmpty()){
                String mensaje = "Error: El item no existe en la base de datos.";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();
                et_ItemEstribadora2.setText("");
                return;
            }
            //System.out.println(listaItems.size());
            super.onPostExecute(aVoid);
        }
    }

    public double calcularPosible(double kgPrecinto, double kgItem,double cantItem){
        double resp= 0;
        kgTotalItem = kgItem * cantItem;
        if(cantItem == 0){
            return 0;
        }
        if(kgPrecinto >= kgTotalItem){
            return cantItem;
        }
        else{
            return calcularPosible(kgPrecinto, kgItem, cantItem-1);
        }
    }

}