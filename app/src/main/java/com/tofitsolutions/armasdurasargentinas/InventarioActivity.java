package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.media.MediaPlayer;

import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMP_TEMPController;

import java.sql.Connection;
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
    private ArrayList<String> materiasPrima;
    private int contadorDeChapasCargadas;
    String loteObtenido;
    String materialObtenido;
    String pesoObtenido;
    String kgReal;
    boolean validacion;
    String loteActual;
    MediaPlayer mp;
    IngresoMPController ingresoMPController = new IngresoMPController();
    IngresoMP_TEMPController ingresoMP_tempController = new IngresoMP_TEMPController();
    IngresoMP ingresoMP;
    String materiaAIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        mp =  MediaPlayer.create(this, R.raw.click);
        contadorDeChapasCargadas = 0;
        tv_contador = (TextView) findViewById(R.id.textView_Contador);
        bt_ok = (Button) findViewById(R.id.button_ok);
        bt_finalizar = (Button) findViewById(R.id.button_Finalizar);
        et_codigoDeBarras = (EditText) findViewById(R.id.editText_CodigoDeBarras);
        et_codigoDeBarras.setHint("Por favor lea el codigo");
        et_codigoDeBarras.setHintTextColor(Color.RED);
        et_kgReal = (EditText) findViewById(R.id.editText_KgReal);
        et_kgReal.setHintTextColor(Color.RED);
        et_kgReal.setHintTextColor(Color.RED);
        tv_informativo = (TextView) findViewById(R.id.textView_Informativo);
        excepcion = (TextView) findViewById(R.id.textView_Exception);
        materiasPrima = new ArrayList<String>();
        materiaAIngresar = "";
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
                    ingresoMP = ingresoMPController.getMP(nro);

                    if(ingresoMP!=null){
                        et_kgReal.requestFocus();
                        et_kgReal.setHint("Por favor ingrese el peso");
                        et_kgReal.setHintTextColor(Color.RED);
                        et_kgReal.setText(ingresoMP.getKgDisponible());
                    }
                    else{

                        et_codigoDeBarras.setText("");
                        et_codigoDeBarras.setHint("Por favor lea el codigo");
                        et_codigoDeBarras.setHintTextColor(Color.RED);
                        et_codigoDeBarras.requestFocus();
                        String mensaje = "Error: El precinto ingresado no existe.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        return;
                    }

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

                if(ingresoMP_tempController.existe(codigoDeBarras)){
                    et_codigoDeBarras.setText("");
                    et_codigoDeBarras.setHint("Por favor lea el codigo");
                    et_codigoDeBarras.setHintTextColor(Color.RED);
                    et_codigoDeBarras.requestFocus();
                    String mensaje = "Error: El precinto ingresado ya fue ingresado antes.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
                if(!esNumero(kgReal)){

                    String mensaje = "Error: El peso debe ser numérico.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                    return;
                }
                if(kgReal.length() == 4) {
                    if (codigoDeBarras.length() == 24) {

                        loteObtenido = codigoDeBarras.substring(0, 10);
                        materialObtenido = codigoDeBarras.substring(10, 20);
                        pesoObtenido = codigoDeBarras.substring(20, 24);
                        System.out.println(loteObtenido);
                        System.out.println(materialObtenido);
                        System.out.println(pesoObtenido);
                        // InventarioActivity.ValidarIngresoMP().execute();


                    }
                    else {
                        et_codigoDeBarras.setText("");
                        et_codigoDeBarras.setHint("Por favor lea el codigo");
                        et_codigoDeBarras.setHintTextColor(Color.RED);
                        et_codigoDeBarras.requestFocus();
                        String mensaje = "Error: Codigo de barras invalido";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        return;
                    }
                }
                else{
                        System.out.println(kgReal);
                    et_kgReal.setText("");

                    et_kgReal.setHint("Por favor reingrese el peso.");
                        et_kgReal.setHintTextColor(Color.RED);
                        et_kgReal.requestFocus();
                        String mensaje = "Error: El peso ingresado debe tener 4 números.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        return;

                }


                materiaAIngresar = codigoDeBarras;

                for(String materia : materiasPrima){
                    if(materiaAIngresar.equals(materia.substring(0,24))){
                        et_codigoDeBarras.setText("");
                        et_kgReal.setText("");
                        et_codigoDeBarras.setHint("Por favor lea el codigo");
                        et_codigoDeBarras.setHintTextColor(Color.RED);
                        et_codigoDeBarras.requestFocus();
                        String mensaje = "Error: Codigo de barras ya ingresado";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        return;
                    }
                }

                ingresoMP = ingresoMPController.getMP(codigoDeBarras);

                if(ingresoMP!=null){
                    et_kgReal.requestFocus();
                    et_kgReal.setHint("Por favor ingrese el peso");
                    et_kgReal.setHintTextColor(Color.RED);
                    et_kgReal.setText(ingresoMP.getKgDisponible());
                }
                else{

                    et_codigoDeBarras.setText("");
                    et_codigoDeBarras.setHint("Por favor lea el codigo");
                    et_codigoDeBarras.setHintTextColor(Color.RED);
                    et_codigoDeBarras.requestFocus();
                    String mensaje = "Error: El precinto ingresado no existe.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                    return;
                }


                et_kgReal.setText("");
                et_codigoDeBarras.setText("");
                et_codigoDeBarras.setHint("Por favor lea el codigo");
                et_codigoDeBarras.setHintTextColor(Color.RED);
                et_codigoDeBarras.requestFocus();
                String mensaje = "Validación correcta.";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();

                materiasPrima.add(materiaAIngresar + kgReal);
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

                for(String codigoDeBarras : materiasPrima){
                    ingresoMP_tempController.insertarIngresoMP_TEMP(codigoDeBarras);

                }
                Statement stmt = con.createStatement();
                String query2="update ajuste_inventario set ajuste=1;";
                stmt.executeUpdate(query2);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(InventarioActivity.this, InicialActivity.class);
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
