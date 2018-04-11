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
import android.widget.EditText;
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
    Button btn_calibrar;
    EditText edt_calibrar;
    int decibelios = -41;

    // Módulos Wifi ESP8266 E01
    //------------------------------------------------
    final String aula101 = "a2:20:a6:01:6c:27"; // "90:67:1c:70:ef:18";//
    final String aula102 = "a2:20:a6:10:81:e5";
    final String aula103 = "a2:20:a6:12:c2:7d";
    //------------------------------------------------

    //Banderas de aulas
    public int a101 = 1;
    public int a102 = 1;
    public int a103 = 1;
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
            sb.append("\nRedes Wifi: ").append(wifiList.size()).append("\n\n");

            for (int i=0; i<wifiList.size(); i++) {
                sb.append(Integer.toString(i + 1)).append("- Red-> ");
                String ssid_red = wifiList.get(i).SSID;
                int dbl_red = wifiList.get(i).level;
                String bssid_red = wifiList.get(i).BSSID;

                ///////////////////////////////////////////////////////////
                if (bssid_red.equals(aula101) && dbl_red >= decibelios && a101 ==1) {
                    playaudio(context, R.raw.aula_101);
                    playaudio(context, R.raw.aula_101);
                    //hilovibrar();
                    a101=0;
                    a102=1;
                    a103=1;
                }

                if (bssid_red.equals(aula102) && dbl_red >= decibelios && a102 ==1) {
                    playaudio(context, R.raw.aula_102);
                    playaudio(context, R.raw.aula_102);
                    //hilovibrar();
                    a101=1;
                    a102=0;
                    a103=1;
                }

                if (bssid_red.equals(aula103) && dbl_red >= decibelios && a103 ==1) {
                    playaudio(context, R.raw.aula_103);
                    playaudio(context, R.raw.aula_103);
                    //hilovibrar();
                    a101=1;
                    a102=1;
                    a103=0;
                }

                if (ssid_red.equals("")) {
                    sb.append(" -- Red Oculta -- ");
                    //continue;
                } else {
                    sb.append(ssid_red);
                }
                sb.append("\n\n");
                sb.append(dbl_red);
                sb.append("\n\n");
                sb.append(bssid_red.toUpperCase());
                sb.append("\n\n");
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

        btn_calibrar = findViewById(R.id.btn_calibrar);
        img_fondo = findViewById(R.id.img_fondo);
        edt_calibrar = findViewById(R.id.edt_calibrar);

        btn_calibrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_fondo.setVisibility(View.VISIBLE);
                tv_redes.setVisibility(View.GONE);
                edt_calibrar.setVisibility(View.GONE);
                String dlb = edt_calibrar.getText().toString();
                decibelios = -Integer.parseInt(dlb);
            }
        });
        btn_calibrar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                img_fondo.setVisibility(View.GONE);
                tv_redes.setVisibility(View.VISIBLE);
                edt_calibrar.setVisibility(View.VISIBLE);
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

