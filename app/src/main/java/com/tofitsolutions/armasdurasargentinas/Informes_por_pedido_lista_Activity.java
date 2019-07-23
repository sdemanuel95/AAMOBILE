package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class Informes_por_pedido_lista_Activity extends AppCompatActivity{


    String pedido;
    String estado;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_informes_por_pedido_lista);
        String pedido = i.getStringExtra("pedido");
        String estado = i.getStringExtra("estado");
        WebView wv = (WebView)findViewById(R.id.web_view);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);

        //wv.loadUrl("file:///android_asset/myweb.html");
        wv.loadUrl("https://armadurasargentinas.herokuapp.com/informesDeclaracion/resumenPedidoItem/" + estado.toLowerCase() + "/" + pedido);
        /*
        TableLayout lista = (TableLayout)findViewById(R.id.lista);
        String [] cadena = new String[2];

        TableRow tableRow = new TableRow(getBaseContext());
        TableRow tableRow2 = new TableRow(getBaseContext());
        TableRow tableRow3 = new TableRow(getBaseContext());
        for(int x = 0; x<2;x++){

            TextView textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(
                    15,15,15,15
            );
            textView.setBackgroundResource(R.color.colorPrimary);
            textView.setTextColor(Color.WHITE);
            textView.setText("holanda");

            tableRow.addView(textView);

        }
        TextView textView = new TextView(getBaseContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(
                15,15,15,15
        );
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        textView.setText("holanda");

        TextView textView2 = new TextView(getBaseContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(
                15,15,15,15
        );
        textView2.setBackgroundResource(R.color.colorPrimary);
        textView2.setTextColor(Color.WHITE);
        textView2.setText("holanda1ssssssssssssssss");



        tableRow2.addView(textView);
        tableRow3.addView(textView2);

        lista.addView(tableRow);
        lista.addView(tableRow2);
        lista.addView(tableRow3);
        */

    }

}
