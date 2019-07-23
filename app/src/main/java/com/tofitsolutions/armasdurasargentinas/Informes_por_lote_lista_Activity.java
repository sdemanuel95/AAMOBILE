package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class Informes_por_lote_lista_Activity extends AppCompatActivity{


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_informes_por_lote_lista);
        String lote = i.getStringExtra("lote");
        WebView wv = (WebView)findViewById(R.id.web_view);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.loadUrl("https://armadurasargentinas.herokuapp.com/informesDeclaracion/getLote/" + lote);

    }

}
