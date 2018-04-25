package com.trackerforview.tracker4view;

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
import android.support.v4.content.ContextCompat;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MediaPlayer play;
    TextView tv_redes;
    WifiReceiver receiver;
    List<ScanResult> wifiList;
    WifiManager wifi;
    ImageView img_fondo;
    Button btn_redes;
    int decibelios = -45;

    // Módulos Wifi ESP8266 E01
    final String adecanato = "62:01:94:3a:6a:5b";
    final String bmujeres102 = "62:01:94:3a:6c:b0";
    final String bhombres = "62:01:94:3a:67:7e";
    final String aula104 = "62:01:94:3a:68:bf";
    final String aula105 = "62:01:94:3a:6a:61";
    final String aula106 = "62:01:94:3a:6a:31";
    final String aula201 = "62:01:94:3a:67:89";
    final String aula202 = "62:01:94:3a:67:7b";
    final String aula203 = "62:01:94:3a:67:9c";
    final String aula204 = "62:01:94:3a:65:f3";
    final String aula205 = "62:01:94:3a:67:93";
    final String aula206 = "62:01:94:3a:6a:54";
    final String aula301 = "62:01:94:3a:6a:5c";
    final String aula302 = "62:01:94:3a:66:3f";
    final String aula303 = "62:01:94:3a:68:cb";
    final String aula304 = "62:01:94:3a:68:52";
    final String aula305 = "62:01:94:3a:67:91";
    final String aula306 = "62:01:94:3a:68:c5";
    //------------------------------------------------

    // Banderas de aulas
    public int bdecanato = 1;
    public int bbmujeres102 = 1;
    public int bbhombres = 1;
    public int a104 = 1;
    public int a105 = 1;
    public int a106 = 1;
    public int a201 = 1;
    public int a202 = 1;
    public int a203 = 1;
    public int a204 = 1;
    public int a205 = 1;
    public int a206 = 1;
    public int a301 = 1;
    public int a302 = 1;
    public int a303 = 1;
    public int a304 = 1;
    public int a305 = 1;
    public int a306 = 1;
    //-------------------------------------------------


    StringBuilder sb = new StringBuilder();

    public class WifiReceiver extends BroadcastReceiver {
        /// Eliminar audio reprodcido
        public void destruir() {
            if (play != null && play.isPlaying())
                play.release();
        }

        public void hilo() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void hilovibrar() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void playaudio(Context context, int audio){
            final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            destruir();
            play = MediaPlayer.create(getApplicationContext(), audio);
            assert vibrator != null;
            vibrator.vibrate(400);
            play.start();
            hilo();
            vibrator.vibrate(400);
            hilovibrar();
            play.stop();
        }

        // Escaneo de redes wifi
        @Override
        public void onReceive(Context context, Intent intent) {
            sb = new StringBuilder();
            wifiList = wifi.getScanResults();

            for (int i=0; i<wifiList.size(); i++) {
                int dbl_red = wifiList.get(i).level;
                String bssid_red = wifiList.get(i).BSSID;

                /// == Condiciones para reproducir los audios de cada aula == ///
                if (bssid_red.equals(adecanato) && dbl_red >= decibelios && bdecanato == 1) {
                    playaudio(context, R.raw.decanato);
                    playaudio(context, R.raw.decanato);
                    bdecanato = 0;
                }

                if (bssid_red.equals(bmujeres102) && dbl_red >= decibelios && bbmujeres102 == 1) {
                    playaudio(context, R.raw.bmujeres_102);
                    playaudio(context, R.raw.bmujeres_102);
                    bbmujeres102 = 0;
                }

                if (bssid_red.equals(bhombres) && dbl_red >= decibelios && bbhombres == 1) {
                    playaudio(context, R.raw.bhombres);
                    playaudio(context, R.raw.bhombres);
                    bbhombres = 0;
                }

                if (bssid_red.equals(aula104) && dbl_red >= decibelios && a104 == 1) {
                    playaudio(context, R.raw.aula_104);
                    playaudio(context, R.raw.aula_104);
                    a104 = 0;
                }

                if (bssid_red.equals(aula105) && dbl_red >= decibelios && a105 == 1) {
                    playaudio(context, R.raw.aula_105);
                    playaudio(context, R.raw.aula_105);
                    a105 = 0;
                }

                if (bssid_red.equals(aula106) && dbl_red >= decibelios && a106 == 1) {
                    playaudio(context, R.raw.aula_106);
                    playaudio(context, R.raw.aula_106);
                    a106 = 0;
                }

                if (bssid_red.equals(aula201) && dbl_red >= decibelios && a201 == 1) {
                    playaudio(context, R.raw.aula_201);
                    playaudio(context, R.raw.aula_201);
                    a201 = 0;
                }

                if (bssid_red.equals(aula202) && dbl_red >= decibelios && a202 == 1) {
                    playaudio(context, R.raw.aula_202);
                    playaudio(context, R.raw.aula_202);
                    a202 = 0;
                }

                if (bssid_red.equals(aula203) && dbl_red >= decibelios && a203 == 1) {
                    playaudio(context, R.raw.aula_203);
                    playaudio(context, R.raw.aula_203);
                    a203 = 0;
                }

                if (bssid_red.equals(aula204) && dbl_red >= decibelios && a204 == 1) {
                    playaudio(context, R.raw.aula_204);
                    playaudio(context, R.raw.aula_204);
                    a204 = 0;
                }

                if (bssid_red.equals(aula205) && dbl_red >= decibelios && a205 == 1) {
                    playaudio(context, R.raw.aula_205);
                    playaudio(context, R.raw.aula_205);
                    a205 = 0;
                }

                if (bssid_red.equals(aula206) && dbl_red >= decibelios && a206 == 1) {
                    playaudio(context, R.raw.aula_206);
                    playaudio(context, R.raw.aula_206);
                    a206 = 0;
                }

                if (bssid_red.equals(aula301) && dbl_red >= decibelios && a301 == 1) {
                    playaudio(context, R.raw.aula_301);
                    playaudio(context, R.raw.aula_301);
                    a301 = 0;
                }

                if (bssid_red.equals(aula302) && dbl_red >= decibelios && a302 == 1) {
                    playaudio(context, R.raw.aula_302);
                    playaudio(context, R.raw.aula_302);
                    a302 = 0;
                }

                if (bssid_red.equals(aula303) && dbl_red >= decibelios && a303 == 1) {
                    playaudio(context, R.raw.aula_303);
                    playaudio(context, R.raw.aula_303);
                    a303 = 0;
                }

                if (bssid_red.equals(aula304) && dbl_red >= decibelios && a304 == 1) {
                    playaudio(context, R.raw.aula_304);
                    playaudio(context, R.raw.aula_304);
                    a304 = 0;
                }

                if (bssid_red.equals(aula305) && dbl_red >= decibelios && a305 == 1) {
                    playaudio(context, R.raw.aula_305);
                    playaudio(context, R.raw.aula_305);
                    a305 = 0;
                }

                if (bssid_red.equals(aula306) && dbl_red >= decibelios && a306 == 1) {
                    playaudio(context, R.raw.aula_306);
                    playaudio(context, R.raw.aula_306);
                    a306 = 0;
                }

                /// == Para que solo se muestren los datos de las redes de los módulos == ///
                if (bssid_red.equals(adecanato) ||
                    bssid_red.equals(bmujeres102) ||
                    bssid_red.equals(bhombres) ||
                    bssid_red.equals(aula104) ||
                    bssid_red.equals(aula105) ||
                    bssid_red.equals(aula106) ||
                    bssid_red.equals(aula201) ||
                    bssid_red.equals(aula202) ||
                    bssid_red.equals(aula203) ||
                    bssid_red.equals(aula204) ||
                    bssid_red.equals(aula205) ||
                    bssid_red.equals(aula206) ||
                    bssid_red.equals(aula301) ||
                    bssid_red.equals(aula302) ||
                    bssid_red.equals(aula303) ||
                    bssid_red.equals(aula304) ||
                    bssid_red.equals(aula305) ||
                    bssid_red.equals(aula306)) {
                        sb.append("╔════════════════════════╗").append("\n║  ");
                        sb.append("  << Módulo ESP >>    ").append("║\n║ Mac: ");
                        sb.append(bssid_red.toUpperCase()).append(" ║\n║ dB: ");
                        sb.append(dbl_red).append("                ║\n");
                        sb.append("╚════════════════════════╝\n");
                }
            }
            tv_redes.setText(sb);
            wifiList.clear();
            wifi.startScan();
        }
    }


    @SuppressLint({"WrongViewCast", "ShowToast", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Para que se habilite subir y bajar el volumen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                cargarDialogoRecomendacion();
            }
            //Permiso para el uso de recursos

            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION );
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                //cargarDialogoRecomendacion();
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    cargarDialogoRecomendacion();
                }else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }else {
                requestPermissions(new String[]{WIFI_SERVICE},100);
            }
        }

        tv_redes = findViewById(R.id.tv_redes);
        wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        assert wifi != null;
        if(!wifi.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(),"Wifi is disabled it",Toast.LENGTH_LONG);
            wifi.setWifiEnabled(true);
        }

        receiver = new WifiReceiver();
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        tv_redes.setText("Buscando Redes Wifi");

        btn_redes = findViewById(R.id.btn_redes);
        img_fondo = findViewById(R.id.img_fondo);

        btn_redes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_fondo.setVisibility(View.VISIBLE);
                tv_redes.setVisibility(View.GONE);
                btn_redes.setText("MOSTRAR REDES");
            }
        });
        btn_redes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                img_fondo.setVisibility(View.GONE);
                tv_redes.setVisibility(View.VISIBLE);
                btn_redes.setText("OCULTAR REDES");
                return true;
            }
        });

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WIFI_SERVICE},100);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                tv_redes.setEnabled(true);
            }else {
                cargarDialogoRecomendacion();
            }
        }

    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
        // para q no se bloquee la pantalla
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}

