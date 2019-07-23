package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.InventarioController;
import com.tofitsolutions.armasdurasargentinas.controllers.SubproductoController;
import com.tofitsolutions.armasdurasargentinas.restControllers.UsuariosAndroidImpl;

public class PrincipalActivity extends AppCompatActivity {

    private Button produccion, inventario, despacho, descarga, loginInventario, stockInicial,informes;
    private InventarioController inventarioController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        inventarioController = new InventarioController();
        produccion = (Button) findViewById(R.id.buttonProduccion);
        inventario = (Button) findViewById(R.id.buttonInventario);
        despacho = (Button) findViewById(R.id.buttonDespacho);
        descarga = (Button) findViewById(R.id.buttonDescarga);
        stockInicial = (Button) findViewById(R.id.buttonStockInicial);
        loginInventario = (Button) findViewById(R.id.buttonLoginInventario);
        informes = (Button) findViewById(R.id.buttonInformes);
        Intent intentPrePrincipal = getIntent();
        final String usuario = intentPrePrincipal.getStringExtra("usuario");
        final String contraseña = intentPrePrincipal.getStringExtra("contraseña");
        final UsuarioAndroid usuarioAndroid = (UsuarioAndroid) intentPrePrincipal.getSerializableExtra("usuarioAndroid");


        SubproductoController s = new SubproductoController();
        produccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inventarioController.validarAjuste()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("No podrá ingresar hasta ajustar inventario desde la web.");
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


                if (!usuarioAndroid.isProduccion()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de producción.");
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


                Intent i = new Intent(PrincipalActivity.this, ProduccionActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("contraseña", contraseña);
                startActivity(i);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inventarioController.validarAjuste()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("No podrá ingresar hasta ajustar inventario desde la web.");
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

                if (!usuarioAndroid.isStock()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de stock.");
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


                Intent i = new Intent(PrincipalActivity.this, DespachoActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }
        });
        despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inventarioController.validarAjuste()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("No podrá ingresar hasta ajustar inventario desde la web.");
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


                if (!usuarioAndroid.isDespacho()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de despacho.");
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


                Intent i = new Intent(PrincipalActivity.this, DespachoActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }
        });
        descarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inventarioController.validarAjuste()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("No podrá ingresar hasta ajustar inventario desde la web.");
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
                if (!usuarioAndroid.isDescarga()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de descarga.");
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
                Intent i = new Intent(PrincipalActivity.this, SesionDescargaActivity.class);
                startActivity(i);
            }
        });


        loginInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!usuarioAndroid.isInventario()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de inventario.");
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


                Intent i = new Intent(PrincipalActivity.this, LoginInventarioActivity.class);
                startActivity(i);
            }
        });

        stockInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inventarioController.validarAjuste()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("No podrá ingresar hasta ajustar inventario desde la web.");
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

                if (!usuarioAndroid.isStockinicial()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de stock inicial.");
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


                Intent i = new Intent(PrincipalActivity.this, StockInicial.class);
                startActivity(i);
            }
        });

        informes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!usuarioAndroid.isStock()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El usuario " + usuarioAndroid.getNombre() + " " + usuarioAndroid.getApellido()
                            + " no cuenta con permisos para acceder al módulo de informes.");
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


                Intent i = new Intent(PrincipalActivity.this, InformesActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
