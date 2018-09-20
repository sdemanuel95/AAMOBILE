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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ValidarChapaActivity extends AppCompatActivity {

    private Button bt_ok, bt_finalizar;
    private EditText et_codigoDeBarras, et_colada, et_pesoPorBalanza;
    private TextView tv_informativo, excepcion, tv_contador;
    private ProgressDialog progress;
    private ArrayList<IngresoMP> materiasPrima;
    private ArrayList<IngresoMP> toRemove;
    private ArrayList<String> numerosDeRemitos;
    private ArrayList<String> numerosDeReferencia;
    private int contadorDeChapasCargadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_chapa);

        contadorDeChapasCargadas = 0;
        tv_contador = (TextView) findViewById(R.id.textView_Contador);
        bt_ok = (Button) findViewById(R.id.button_ok);
        bt_finalizar = (Button) findViewById(R.id.button_Finalizar);
        et_codigoDeBarras = (EditText) findViewById(R.id.editText_CodigoDeBarras);
        et_codigoDeBarras.setInputType(InputType.TYPE_NULL);
        et_codigoDeBarras.setHint("Por favor lea el codigo");
        et_codigoDeBarras.setHintTextColor(Color.RED);
        et_colada = (EditText) findViewById(R.id.editText_Colada);
        et_colada.setInputType(InputType.TYPE_NULL);
        et_pesoPorBalanza = (EditText) findViewById(R.id.editText_PesoPorBalanza);
        et_pesoPorBalanza.setInputType(InputType.TYPE_NULL);
        tv_informativo = (TextView) findViewById(R.id.textView_Informativo);
        excepcion = (TextView) findViewById(R.id.textView_Exception);
        materiasPrima = new ArrayList<IngresoMP>();
        toRemove = new ArrayList<IngresoMP>();
        new IngresoMPQuery().execute();

        Intent intentRemitoMP = getIntent();
        numerosDeRemitos = (ArrayList<String>) intentRemitoMP.getSerializableExtra("remitos");
        numerosDeReferencia = (ArrayList<String>) intentRemitoMP.getSerializableExtra("referencias");

        et_codigoDeBarras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String nro = editable.toString();
                if (nro.length() == 24){
                    et_colada.requestFocus();
                    et_colada.setHint("Por favor ingrese la colada");
                    et_colada.setHintTextColor(Color.RED);
                }
            }
        });

        et_colada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String nro = editable.toString();
                if (nro.length() == 10){
                    et_pesoPorBalanza.requestFocus();
                    et_pesoPorBalanza.setHint("Por favor ingrese el peso");
                    et_pesoPorBalanza.setHintTextColor(Color.RED);
                }
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoDeBarras = et_codigoDeBarras.getText().toString();
                String colada = et_colada.getText().toString();
                if (codigoDeBarras.length() == 24) {
                    if (colada.length() == 10) {
                        String lote = codigoDeBarras.substring(0, 10);
                        String material = codigoDeBarras.substring(10, 20);
                        String peso = codigoDeBarras.substring(20, 24);
                        Log.d("Cantidad MP: ", materiasPrima.size() + "");
                        for (int index = 0; index < materiasPrima.size(); index++) {
                            IngresoMP mpActual = materiasPrima.get(index);
                            Log.d("LOTE: ",mpActual.getLote() + " = " + lote);
                            Log.d("PESO: ",mpActual.getCantidad() + " = " + peso);
                            Log.d("COD MATERIAL: ",mpActual.getMaterial() + " = " + material);
                            if (mpActual.getLote().equals(lote) && mpActual.getCantidad().equals(peso) && mpActual.getMaterial().equals(material)) {
                                //FALTA TERMINAR
                                for (int i = 0; i < numerosDeReferencia.size(); i++) {
                                    String numRef = numerosDeReferencia.get(i);
                                    if (numRef.equals(mpActual.getReferencia()) && mpActual.getColada().equals("")) {
                                        mpActual.setColada(colada);
                                        if (!et_pesoPorBalanza.getText().toString().equals("")) {
                                            mpActual.setPesoPorBalanza(et_pesoPorBalanza.getText().toString());
                                        }
                                        et_codigoDeBarras.setText("");
                                        et_colada.setText("");
                                        et_pesoPorBalanza.setText("");
                                        contadorDeChapasCargadas++;
                                        tv_contador.setText(contadorDeChapasCargadas + " / " + materiasPrima.size());
                                        i = numerosDeReferencia.size();
                                        index = materiasPrima.size();
                                        tv_informativo.setText("");
                                    } else {
                                        Log.d("I: ", i + "");
                                        Log.d("SIZE: ", numerosDeReferencia.size() -1 + "");
                                        String mensaje = "Error: Chapa ya ingresada";
                                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                                        msjToast.show();
                                    }
                                }
                            } else {
                                String mensaje = "Error: El codigo ingresado no es valido";
                                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                                //msjToast.show();
                            }
                        }
                    } else {
                        String mensaje = "Error: Colada inválida";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }
                } else {
                    String mensaje = "Error: Codigo de barras invalido";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }

                et_codigoDeBarras.requestFocus();
                et_colada.setHint("Colada");
                et_colada.setHintTextColor(Color.WHITE);
                et_pesoPorBalanza.setHint("Peso por balanza");
                et_pesoPorBalanza.setHintTextColor(Color.WHITE);
            }
        });

        bt_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean estaCompleto = true;
                for (IngresoMP mp : materiasPrima) {
                    String colada = mp.getColada();
                    if (colada.equals("")) {
                        estaCompleto = false;
                    }
                }
                if (estaCompleto) {
                    new setChapa().execute();
                    //Intent i = new Intent(ValidarChapaActivity.this, PrincipalActivity.class);
                    //finish();
                    //startActivity(i);
                } else {
                    String mensaje = "Error: No se terminaron de cargar las chapas";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            }
        });
    }


    private class IngresoMPQuery extends AsyncTask<Void, Void, Void> {

        private ArrayList<IngresoMP> materiasPrimaBD;

        @Override
        protected Void doInBackground(Void... params) {

            materiasPrimaBD = new ArrayList<IngresoMP>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM ingresomp");
                while (rs.next()) {
                    long id = rs.getInt("ID");
                    Date fecha = rs.getDate("Fecha");
                    String referencia = rs.getString("Referencia");
                    String material = rs.getString("Material");
                    String descripcion = rs.getString("Descripcion");
                    String cantidad = rs.getString("Cantidad");
                    String umb = rs.getString("UMB");
                    String lote = rs.getString("Lote");
                    String destinatario = rs.getString("Destinatario");
                    String colada = rs.getString("Colada");
                    String pesoPorBalanza = rs.getString("PesoPorBalanza");
                    materiasPrimaBD.add(new IngresoMP(id, fecha, referencia, material, descripcion, cantidad, umb, lote, destinatario, colada, pesoPorBalanza));
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
            materiasPrima = materiasPrimaBD;
            // Se deja en el ArrayList materiasPrima solo los nros de referencia a ingresar.
            for (int i = 0; i < materiasPrima.size(); i++) {
                if (!numerosDeReferencia.contains(materiasPrima.get(i).getReferencia())) {
                    toRemove.add(materiasPrima.get(i));
                }
            }
            materiasPrima.removeAll(toRemove);
            toRemove.clear();
            super.onPostExecute(aVoid);
        }
    }

    private class setChapa extends AsyncTask<Void, Integer, Void> {

        private int progreso = 0;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(ValidarChapaActivity.this);
            progress.setMessage("Guardando");
            progress.setTitle("Chapas");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();

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

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                progress.setMax(materiasPrima.size());
                for (IngresoMP mp : materiasPrima) {
                    String colada = mp.getColada();
                    String pesoPorBalanza = mp.getPesoPorBalanza();
                    long id = mp.getId();
                    stmt.executeUpdate("UPDATE ingresomp SET Colada = '" + colada + "' WHERE ID = '" + id + "'");
                    stmt.executeUpdate("UPDATE ingresomp SET PesoPorBalanza = '" + pesoPorBalanza + "' WHERE ID = '" + id + "'");
                    stmt.executeUpdate("UPDATE stock SET KGTeorico = KGTeorico +'" + mp.getCantidad() + "' WHERE CodMat = '" + mp.getMaterial() + "'");
                    stmt.executeUpdate("UPDATE stock SET KGDisponible = KGDisponible +'" + mp.getCantidad() + "' WHERE CodMat = '" + mp.getMaterial() + "'");
                    progreso++;
                    publishProgress(progreso);
                }
                for (int i = 0; i < numerosDeRemitos.size(); i++) {
                    String nroRemito = numerosDeRemitos.get(i);
                    String nroReferencia = numerosDeReferencia.get(i);
                    stmt.executeUpdate("UPDATE ingresoremitos SET NroRemito = '" + nroRemito + "' WHERE Referencia = '" + nroReferencia + "'");
                    stmt.executeUpdate("UPDATE ingresoremitos SET Estado = '" + "HISTORICO" + "' WHERE Referencia = '" + nroReferencia + "'");
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
            Intent i = new Intent(ValidarChapaActivity.this, PrincipalActivity.class);
            finish();
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }


}
