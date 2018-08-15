package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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

import android.media.MediaPlayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
public class InventarioActivity extends AppCompatActivity {
    private Button bt_ok, bt_finalizar;
    private EditText et_codigoDeBarras, et_kgReal;
    private TextView tv_informativo, excepcion, tv_contador;
    private ProgressDialog progress;
    private ArrayList<MateriaPrima_temp> materiasPrima;
    private int contadorDeChapasCargadas;
    String loteObtenido;
    String materialObtenido;
    String pesoObtenido;
    String kgReal;
    boolean validacion;
    String loteActual;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        mp =  MediaPlayer.create(this, R.raw.click);
        contadorDeChapasCargadas = 0;
        tv_contador = (TextView) findViewById(R.id.textView_Contador);
        bt_ok = (Button) findViewById(R.id.button_ValidarChapa);
        bt_finalizar = (Button) findViewById(R.id.button_Finalizar);
        et_codigoDeBarras = (EditText) findViewById(R.id.editText_CodigoDeBarras);
        et_codigoDeBarras.setInputType(InputType.TYPE_NULL);
        et_codigoDeBarras.setHint("Por favor lea el codigo");
        et_codigoDeBarras.setHintTextColor(Color.RED);
        et_kgReal = (EditText) findViewById(R.id.editText_KgReal);
        et_kgReal.setInputType(InputType.TYPE_NULL);
        et_kgReal.setHintTextColor(Color.RED);
        et_kgReal.setHintTextColor(Color.RED);
        tv_informativo = (TextView) findViewById(R.id.textView_Informativo);
        excepcion = (TextView) findViewById(R.id.textView_Exception);
        materiasPrima = new ArrayList<MateriaPrima_temp>();

        Intent intentRemitoMP = getIntent();

        et_codigoDeBarras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String nro = editable.toString();
                if (nro.length() == 24){
                    et_kgReal.requestFocus();
                    et_kgReal.setHint("Por favor ingrese el peso");
                    et_kgReal.setHintTextColor(Color.RED);
                    et_kgReal.setText(et_codigoDeBarras.getText().toString().substring(20));
                }
            }
        });


        et_codigoDeBarras.setEnabled(true);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                System.out.println("Cantidad de materias ingresadas "+ materiasPrima.size());
                validacion = false;
                String codigoDeBarras = et_codigoDeBarras.getText().toString();
                loteActual = codigoDeBarras;
                kgReal = et_kgReal.getText().toString();
                System.out.println(kgReal);
                if(!esNumero(kgReal)){

                    String mensaje = "Error: El peso debe ser numérico.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();return;
                }
                if(kgReal.length() != 0) {
                    if (codigoDeBarras.length() == 24) {

                        loteObtenido = codigoDeBarras.substring(0, 10);
                        materialObtenido = codigoDeBarras.substring(10, 20);
                        pesoObtenido = codigoDeBarras.substring(20, 24);
                        System.out.println(loteObtenido);
                        System.out.println(materialObtenido);
                        System.out.println(pesoObtenido);
                        new InventarioActivity.ValidarIngresoMP().execute();


                    }
                    else {
                        String mensaje = "Error: Codigo de barras invalido";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }
                }
                else{
                    System.out.println(kgReal);
                        String mensaje = "Error: El peso ingresado no puede ser vacío.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();

                }

                et_codigoDeBarras.requestFocus();
            }
        });
        //boton finalizar
        bt_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                new InventarioActivity.guardarMateriasPrimas().execute();
            }
        });
    }

    private class ValidarIngresoMP extends AsyncTask<Void, Void, Void> {

        private ArrayList<IngresoMP> materiasPrimaBD;
        private ArrayList<MateriaPrima_temp> materiasPrimaTempBD;
        private boolean repetido = false;
        @Override
        protected Void doInBackground(Void... params) {

            materiasPrimaBD = new ArrayList<IngresoMP>();
            materiasPrimaTempBD = new ArrayList<MateriaPrima_temp>();
            Conexion conexion = new Conexion();
            validacion = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection con = conexion.crearConexion();


                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM ingresomp where lote = '" + loteObtenido + "' AND material = '"
                        + materialObtenido + "' AND cantidad = '" + pesoObtenido + "';");
                while (rs.next()) {
                    long id = rs.getInt("ID");
                    //String fecha = rs.getString("Fecha");
                    String referencia = rs.getString("Referencia");
                    String material2 = rs.getString("Material");
                    String descripcion = rs.getString("Descripcion");
                    String cantidad = rs.getString("Cantidad");
                    String umb = rs.getString("UMB");
                    String lote2 = rs.getString("Lote");
                    String destinatario = rs.getString("Destinatario");
                    String colada = rs.getString("Colada");
                    String pesoPorBalanza = rs.getString("PesoPorBalanza");

                    materiasPrimaBD.add(new IngresoMP(id, new Date(), referencia, material2, descripcion, cantidad, umb, lote2, destinatario, colada, pesoPorBalanza));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection con2 = conexion.crearConexion();


                Statement stmt2 = con2.createStatement();
                final ResultSet rs2 = stmt2.executeQuery("SELECT * FROM ingresomp_temp where lote ='" + loteObtenido + "' AND material = '"
                        + materialObtenido + "' AND KGInicial= '" + pesoObtenido + "';");
                while (rs2.next()) {
                    long id = rs2.getInt("ID");
                    //String fecha = rs.getString("Fecha");
                    String material2 = rs2.getString("Material");
                    String descripcion = rs2.getString("Descripcion");
                    String kgInicial = rs2.getString("KGInicial");
                    String umb = rs2.getString("UMB");
                    String lote2 = rs2.getString("Lote");
                    String destinatario = rs2.getString("Destinatario");
                    String colada = rs2.getString("Colada");
                    String pesoPorBalanza = rs2.getString("PesoPorBalanza");
                    String kgEnPlanta = rs2.getString("KGEnPlanta");

                    materiasPrimaTempBD.add(new MateriaPrima_temp(id, "",  material2, descripcion, kgInicial, umb, lote2, destinatario, colada, pesoPorBalanza,kgEnPlanta));
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
            System.out.println("Cantidad de materias encontradas "+ materiasPrimaBD.size());
            System.out.println("Cantidad de materias TEMP encontradas "+ materiasPrimaTempBD.size());
            for(MateriaPrima_temp mpt : materiasPrima){
                if(mpt.getLote().equals(loteObtenido) && mpt.getMaterial().equals(materialObtenido) && mpt.getKgInicial().equals(pesoObtenido)){
                    Toast msjToast = Toast.makeText(getApplicationContext(), "Validación incorrecta, ya ingresó ese número de lote.", Toast.LENGTH_LONG);
                    msjToast.show();
                    return;
                }
            }


            System.out.println(materiasPrimaTempBD.size());
            if(materiasPrimaBD.size() == 0){

                validacion = false;
                et_codigoDeBarras.setText("");
                et_kgReal.setText("");
                Toast msjToast = Toast.makeText(getApplicationContext(), "Validación incorrecta, verificar los datos", Toast.LENGTH_LONG);
                msjToast.show();
                //"mandar mail"
                new MailJob("emanuelsuarezarmaduras@gmail.com", "armaduras10").execute(
                        new MailJob.Mail("emanuelsuarezarmaduras@gmail.com", "sd.emanuel95@gmail.com", "Equipo de desarrollo y soporte de Armaduras Argentinas", "Se ha ingresado un codigo de barras inválido, el lote invalido es = " + loteActual   )
                );
                new MailJob("emanuelsuarezarmaduras@gmail.com", "armaduras10").execute(
                        new MailJob.Mail("emanuelsuarezarmaduras@gmail.com", "sd.emanuel95@gmail.com", "Equipo de desarrollo y soporte de Armaduras Argentinas", "Se ha ingresado un codigo de barras inválido, el lote invalido es = " + loteActual   )
                );
            }

            else{
                if(materiasPrimaTempBD.size()>=1){
                    validacion = false;
                    et_codigoDeBarras.setText("");
                    et_kgReal.setText("");
                    Toast msjToast = Toast.makeText(getApplicationContext(), "Validación incorrecta, el lote ingresado ya se encuentra registrado.", Toast.LENGTH_LONG);
                    msjToast.show();
                    //"mandar mail"
                    new MailJob("emanuelsuarezarmaduras@gmail.com", "armaduras10").execute(
                            new MailJob.Mail("SoporteArmadurasArgentinas@gmail.com", "sd.emanuel95@gmail.com", "Equipo de desarrollo y soporte de Armaduras Argentinas", "Se ha ingresado un codigo de barras que ya existe en el inventario, el lote invalido es = " + loteActual   )
                    );
                    new MailJob("emanuelsuarezarmaduras@gmail.com", "armaduras10").execute(
                            new MailJob.Mail("SoporteArmadurasArgentinas@gmail.com", "sd.emanuel95@gmail.com", "Equipo de desarrollo y soporte de Armaduras Argentinas", "Se ha ingresado un codigo de barras que ya existe en el inventario, el lote invalido es = " + loteActual   )
                    );

                }
                else{
                    System.out.println(materiasPrimaTempBD.size());

                    et_codigoDeBarras.setText("");
                    et_kgReal.setText("");
                    for(IngresoMP IngresoMP : materiasPrimaBD){
                        String lote = IngresoMP.getLote();
                        String material = IngresoMP.getMaterial();
                        String cantidad = IngresoMP.getCantidad();
                        String descripcion = IngresoMP.getDescripcion();
                        String pesoPorBalanza = IngresoMP.getPesoPorBalanza();
                        String umb = IngresoMP.getUmb();
                        String colada = IngresoMP.getColada();
                        String destinatario = IngresoMP.getDestinatario();

                        materiasPrima.add(new MateriaPrima_temp(material,descripcion,cantidad,umb,lote,destinatario,colada,pesoPorBalanza,kgReal));

                        Toast msjToast = Toast.makeText(getApplicationContext(), "Validación correcta", Toast.LENGTH_LONG);
                        msjToast.show();

                    }
                }

            }
            super.onPostExecute(aVoid);
        }
    }

    private class guardarMateriasPrimas extends AsyncTask<Void, Integer, Void> {

        private int progreso = 0;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(InventarioActivity.this);
            progress.setMessage("Guardando");
            progress.setTitle("Materia Prima");
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
            progress.setMax(materiasPrima.size());

            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection con = conexion.crearConexion();
                for(MateriaPrima_temp IngresoMP_temp : materiasPrima){
                    Statement stmt = con.createStatement();
                    String material = IngresoMP_temp.getMaterial();
                    String fecha = IngresoMP_temp.getFecha();
                    String descripcion = IngresoMP_temp.getDescripcion();
                    String kgInicial = IngresoMP_temp.getKgInicial();
                    String UMB = IngresoMP_temp.getUmb();
                    String lote = IngresoMP_temp.getLote();
                    String destinatario = IngresoMP_temp.getDestinatario();
                    String colada = IngresoMP_temp.getColada();
                    String pesoPorBalanza = IngresoMP_temp.getPesoPorBalanza();
                    String kgEnPlanta = IngresoMP_temp.getKgEnPlanta();
                    String query = "INSERT INTO ingresomp_temp values (default, default, '"+material+
                            "','"+descripcion+"','"+kgInicial+"','"+UMB+"','"+ lote
                            +"','"+destinatario+"','"+colada+"','"+pesoPorBalanza+"','"+kgEnPlanta+"');";
                    System.out.println(query);
                    stmt.executeUpdate(query);
                    String query2="update ajuste_inventario set ajuste=1;";
                    stmt.executeUpdate(query2);

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
            Intent i = new Intent(InventarioActivity.this, PrincipalActivity.class);
            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }

    private boolean esNumero(String num){
        try{
            double l = Double.parseDouble(num);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}