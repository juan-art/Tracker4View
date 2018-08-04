package com.voicehelper.voiper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationManager;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    WifiReceiver receiver;
    LocationManager locationManager;
    AlertDialog alert = null;
    final int REQUEST_ACCES_FINE = 0;
    WifiManager wifi;
    MediaPlayer play;
    List<ScanResult> wifiList;
    TextView tv_redes;
    ImageView img_fondo;
    Button btn_redes;
    int decibelios = -48;// normal //-63; baja 46
    int decilejos = -75;//-95;

    // Módulos Wifi ESP8266 E01 //
    final String aula101       = "62:01:94:3a:67:9c";
    final String aula103       = "62:01:94:3a:68:bf";
    final String aula105       = "62:01:94:3a:6a:61";
    final String aula107       = "62:01:94:3a:6a:31";
    final String aula109       = "62:01:94:3a:65:f3";
    final String aula204       = "62:01:94:3a:67:93";
    final String bhombres      = "62:01:94:3a:68:52";
    final String bmujeres      = "62:01:94:3a:6a:5c";
    final String camgesell     = "62:01:94:3a:67:89";
    final String adecanato     = "62:01:94:3a:6a:5b";
    final String depccomun     = "62:01:94:3a:67:91";
    final String depcjurid     = "62:01:94:3a:68:cb";
    final String depcsoccomp   = "62:01:94:3a:67:7b";
    final String dirtrabsoc    = "62:01:94:3a:6a:54";
    final String salactos      = "62:01:94:3a:66:3f";
    final String uni_inclusion = "62:01:94:3a:6c:b0";
    //final String modulo1     = "62:01:94:3a:67:7e";
    //final String modulo2     = "62:01:94:3a:68:c5";
    //------------------------------------------------

    // Banderas de aulas
    public int baula101     = 1;
    public int baula103     = 1;
    public int baula105     = 1;
    public int baula107     = 1;
    public int baula109     = 1;
    public int baula204     = 1;
    public int bbhombres    = 1;
    public int bbmujeres    = 1;
    public int bcamgesell   = 1;
    public int bdecanato    = 1;
    public int bdepccomun   = 1;
    public int bdepcjurid   = 1;
    public int bdepcsoccomp = 1;
    public int bdirtrabsoc  = 1;
    public int bsalactos    = 1;
    public int buinclusion  = 1;
    //-------------------------------------------------

    // Agregado de la aplicación CLOVER
    // Especificacion de los puntos de referencia
    //-----------------------------------------------------------//----
    /*final String informatica = "ESP_06A26F";                     //----
    final String administracionyauditoria = "ESP_06A263";        //----
    final String Rocas = "ESP_06A09D";                           //----
    //-----------------------------------------------------------//----

    //-----------VARIABLES Y BANDERAS ------------//--
    int acercandoseadminyauditoria = 0;           //--
    int puntoAdmin = 0;                           //--
    int acercandoseafci = 0;                      //--
    int puntoFci = 0;                             //--
    int acercandoseecon = 0;                      //--
    int puntoDptoRocas = 0;                       //--
    int banderaDerRocas = 0;                      //--
    int banderafciderec = 0;                      //--
    int banderafciizq = 0;                        //--
    int banderaaudiderec = 0;                     //--
    int banderaaudiizq = 1;                       //--
    int banderaIzqRocasptoRocas = 1;              //--
    int puntofci = 0;                             //--
    int x = 40;                                   //--*/
    //--------------------------------------------//--

    public class WifiReceiver extends BroadcastReceiver{
        // Escaneo de redes wifi
        @Override
        public void onReceive(Context context, Intent intent){
            StringBuilder sb = new StringBuilder();
            wifiList = wifi.getScanResults();

            for (int i=0; i<wifiList.size(); i++){
                int dbl_red = wifiList.get(i).level;
                String bssid_red = wifiList.get(i).BSSID;
                /*String ssid_red = wifiList.get(i).SSID;

                //------------Ajuste deteccion de redes------------- (CLOVER)
                int AADMIN =  -x-10; // -75; //Acercadose admin
                int PADMIN =  -x;    // -65; //Admin punto de deteccion
                int AFCI =    -x;    // -65; //Acercadose FCI
                int PFCI =    -x;    // -65; //FCI punto de deteccion REF -75 CERCA -60 MAS CERCA
                int AROCAS =  -x-10; // -75; //Acercadose ROCAS
                int PROCAS =  -x;    // -65; //ROCAS punto de deteccion*/

                /// == Condiciones para reproducir los audios de cada aula == ///
                switch (bssid_red){
                    case aula101: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula101 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_101);
                            baula101 = 0;
                        } else if (dbl_red <= (decilejos)) baula101 = 1;
                        break;
                    }

                    case aula103: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula103 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_103);
                            baula103 = 0;
                        } else if (dbl_red <= (decilejos)) baula103 = 1;
                        break;
                    }

                    case aula105: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula105 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_105);
                            baula105 = 0;
                        } else if (dbl_red <= (decilejos)) baula105 = 1;
                        break;
                    }

                    case aula107: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula107 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_107);
                            baula107 = 0;
                        } else if (dbl_red <= (decilejos)) baula107 = 1;
                        break;
                    }

                    case aula109: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula109 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_109);
                            baula109 = 0;
                        } else if (dbl_red <= (decilejos)) baula103 = 1;
                        break;
                    }

                    case aula204: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && baula204 == 1 && dbl_red < -10){
                            playaudio(context, R.raw.aula_204);
                            baula204 = 0;
                        } else if (dbl_red <= (decilejos)) baula204 = 1;
                        break;
                    }

                    case bhombres: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bbhombres == 1 && dbl_red < -10){
                            playaudio(context, R.raw.b_hombres);
                            bbhombres = 0;
                        } else if (dbl_red <= (decilejos)) bbhombres = 1;
                        break;
                    }

                    case bmujeres: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bbmujeres == 1 && dbl_red < -10){
                            playaudio(context, R.raw.b_mujeres);
                            bbmujeres = 0;
                        } else if (dbl_red <= (decilejos)) bbmujeres = 1;
                        break;
                    }

                    case camgesell: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bcamgesell == 1 && dbl_red < -10){
                            playaudio(context, R.raw.cam_gesell);
                            bcamgesell = 0;
                        } else if (dbl_red <= (decilejos)) bcamgesell = 1;
                        break;
                    }

                    case adecanato: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bdecanato == 1 && dbl_red < -10){
                            playaudio(context, R.raw.decanato);
                            bdecanato = 0;
                        } else if (dbl_red <= (decilejos)) bdecanato = 1;
                        break;
                    }

                    case depccomun: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bdepccomun == 1 && dbl_red < -10){
                            playaudio(context, R.raw.dep_ccomunicacion);
                            bdepccomun = 0;
                        } else if (dbl_red <= (decilejos)) bdepccomun = 1;
                        break;
                    }

                    case depcjurid: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bdepcjurid == 1 && dbl_red < -10){
                            playaudio(context, R.raw.dep_cjuridicas);
                            bdepcjurid = 0;
                        } else if (dbl_red <= (decilejos)) bdepcjurid = 1;
                        break;
                    }

                    case depcsoccomp: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bdepcsoccomp == 1 && dbl_red < -10){
                            playaudio(context, R.raw.dep_csocialescomp);
                            bdepcsoccomp = 0;
                        } else if (dbl_red <= (decilejos)) bdepcsoccomp = 1;
                        break;
                    }

                    case dirtrabsoc: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bdirtrabsoc == 1 && dbl_red < -10){
                            playaudio(context, R.raw.dir_trabajosocial);
                            bdirtrabsoc = 0;
                        } else if (dbl_red <= (decilejos)) bdirtrabsoc = 1;
                        break;
                    }

                    case salactos: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && bsalactos == 1 && dbl_red < -10){
                            playaudio(context, R.raw.salon_actos);
                            bsalactos = 0;
                        } else if (dbl_red <= (decilejos)) bsalactos = 1;
                        break;
                    }

                    case uni_inclusion: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red >= decibelios && buinclusion == 1 && dbl_red < -10){
                            playaudio(context, R.raw.uni_inclusion);
                            buinclusion = 0;
                        } else if (dbl_red <= (decilejos)) buinclusion = 1;
                        break;
                    }

                    default: break;
                }

                // Condiciones de CLOVER //
                /*switch(ssid_red){
                    case administracionyauditoria: {
                        appendered(dbl_red, bssid_red, sb);
                        if (dbl_red <= AADMIN){
                            acercandoseadminyauditoria = 1;
                            puntoAdmin = 0;
                            banderaaudiizq = 1;
                        }
                        //  PUNTO ADMIN Y AUDITORIA
                        else if (dbl_red >= PADMIN){
                            puntoAdmin = 1;
                            acercandoseadminyauditoria = 0;
                        }
                        break;
                    }

                    case informatica: {
                        appendered(dbl_red, bssid_red, sb);
                        // Acercandose a la facultad ciecnias informaticas
                        if (dbl_red <= AFCI){
                            acercandoseafci = 1;
                            puntoFci = 0;
                        }
                        // DPUNTO FACULTAD CIENCIAS INFORMATICAS
                        else if (dbl_red >= PFCI){
                            acercandoseafci = 0;
                            puntoFci = 1;
                        }
                        break;
                    }

                    case Rocas: {
                        appendered(dbl_red, bssid_red, sb);
                        // ACERCANDOSE DPTO. ROCAS
                        if (dbl_red <= AROCAS){
                            banderaIzqRocasptoRocas = 1;
                            acercandoseecon = 1;
                            puntoDptoRocas = 0;
                        }

                        // PUNTO DPTO ROCAS
                        else if (dbl_red >= PROCAS){
                            puntoDptoRocas = 1;
                            acercandoseecon = 0;
                        }
                        break;
                    }

                    default: break;
                }

                // Derechha facultad de administración y auditoria
                if (puntoAdmin == 1 && banderaaudiderec == 1){
                    playaudio(context, R.raw.deradminyaudi);
                    banderafciderec = 1;
                    banderafciizq = 0;
                    banderaaudiizq = 0;
                    banderaaudiderec = 0;
                    puntoFci = 0;
                }

                // Izquierda facultad de administración y auditoria
                else if (puntoAdmin == 1 && banderaaudiizq == 1 && puntoFci == 0 && banderaaudiderec == 0){
                    playaudio(context, R.raw.izqadminyaudi);
                    banderafciderec = 1;
                    banderaaudiizq = 0;
                }

                // Derecha facultad de ciencias informáticas
                else if (puntoFci == 1 && banderafciderec == 1){
                    playaudio(context, R.raw.derinformatica);
                    banderaDerRocas = 1;
                    banderaaudiizq = 0;
                    banderafciderec = 0;
                    banderaaudiderec = 1;
                    puntoAdmin = 0;
                }

                // Izquierda facultad de ciencias informáticas
                else if (puntoFci == 1 && banderafciizq == 1){
                    playaudio(context, R.raw.izqinformatica);
                    banderafciizq = 0;
                    puntoFci = 0;
                    banderaaudiderec = 1;
                    banderaDerRocas = 1;
                    puntoDptoRocas = 0;
                }

                // Punto facultad de ciencia informáticas
                else if (puntoFci == 1 && banderafciizq == 0 && banderafciderec == 0 && puntofci <= 1 && banderaaudiderec == 0 && banderaDerRocas == 0){
                    banderaDerRocas = 1;
                    puntoDptoRocas = 0;
                    banderaaudiderec = 1;
                    puntoFci = 0;
                    puntofci ++;
                }

                // IZQUIERDA DEPARTAMENTO DE SUELO Y ROCAS
                else if (puntoDptoRocas == 1 && banderaIzqRocasptoRocas == 1 && puntoFci == 0 && banderaDerRocas == 0){
                    playaudio(context, R.raw.izqdeproca);
                    banderafciizq = 1;
                    banderaIzqRocasptoRocas = 0;
                    puntoDptoRocas = 0;
                }

                // DERECHA DEPARTAMENTO DE SUELOS Y ROCAS
                else if (banderaDerRocas == 1 && puntoFci == 0 && puntoDptoRocas == 1 && banderafciizq == 0 && puntoFci == 0){
                    playaudio(context, R.raw.derdeproca);
                    banderaDerRocas = 0;
                    banderafciizq = 1;
                    puntoDptoRocas = 0;
                    banderaIzqRocasptoRocas = 0;
                    puntoFci = 0;
                }*/
            }
            tv_redes.setText(sb);
            wifiList.clear();
            wifi.startScan();
        }
    }

    // Función para eliminar audio reprodcido
    public void destruir(){
        if (play != null && play.isPlaying())
            play.release();
    }

    // Función para pausar el hilo que se reproduce el audio y suene el audio completo
    public void hilodelay(long miliseg){
        try {
            Thread.sleep(miliseg);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    // Función para agrega los caracteres a la lista para que se imprima los detalles de la red
    public void appendered(int dbl_red, String bssid_red, StringBuilder stb){
        stb.append("╔════════════════════════╗").append("\n║  ");
        stb.append("  << Módulo ESP >>    ").append("║\n║ Mac: ");
        stb.append(bssid_red.toUpperCase()).append(" ║\n║ dB: ");
        stb.append(dbl_red).append("                ║\n");
        stb.append("╚════════════════════════╝\n");
    }

    // Función para reproduce el audio en un nuevo hilo de proceso
    public void playaudio(final Context context, final int audio){
        Thread playThread = new Thread(){
            public void run(){
                final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                destruir();
                play = MediaPlayer.create(getApplicationContext(), audio);
                assert vibrator != null;
                vibrator.vibrate(350);
                hilodelay(600);
                play.start();
                hilodelay(4100);
                vibrator.vibrate(350);
                play.stop();
                destruir();
            }
        };
        playThread.start();
    }

    public void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        playaudio(this , R.raw.ubicacion);
        builder.setMessage("La ubicación esta desactivada, ¿Desea activarla?")
            .setCancelable(false)
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                    dialog.cancel();
                }
            });
        alert = builder.create();
        alert.show();
    }


    @SuppressLint({"WrongViewCast", "ShowToast", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Selecciona el canal de salida del audio en el teléfono
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Si es versión M o superior solicita los permisos de ubicación
        if(Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCES_FINE);
            }

            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // Si no está activada la ubicación pregunta si desea activarla
            assert locationManager != null;
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                AlertNoGps();
            }
        }

        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        // Activa el WiFi si está desactivado
        assert wifi != null;
        if(!wifi.isWifiEnabled()){
            wifi.setWifiEnabled(true);
        }

        receiver = new WifiReceiver();
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();

        tv_redes = findViewById(R.id.tv_redes);
        btn_redes = findViewById(R.id.btn_redes);
        img_fondo = findViewById(R.id.img_fondo);

        btn_redes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                img_fondo.setVisibility(View.VISIBLE);
                tv_redes.setVisibility(View.GONE);
                btn_redes.setText("MOSTRAR MÓDULOS");
                wifi.startScan();
            }
        });

        btn_redes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                img_fondo.setVisibility(View.GONE);
                tv_redes.setVisibility(View.VISIBLE);
                btn_redes.setText("OCULTAR MÓDULOS");
                wifi.startScan();
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCES_FINE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos brindados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Para que no se bloquee la pantalla
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        assert wifi != null;
        if(!wifi.isWifiEnabled()){
            wifi.setWifiEnabled(true);
        }
        wifi.startScan();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        assert wifi != null;
        if(!wifi.isWifiEnabled()){
            wifi.setWifiEnabled(true);
        }
        wifi.startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        assert wifi != null;
        if(!wifi.isWifiEnabled()){
            wifi.setWifiEnabled(true);
        }
        wifi.startScan();
    }
}
