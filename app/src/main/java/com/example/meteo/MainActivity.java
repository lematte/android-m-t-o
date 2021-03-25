package com.example.meteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
TextView tvCompteur;
Button btnOk;
TextView tvTempératur;
TextView tvVille;
TextView tvTempératuremin;
TextView tvTemperaturemax;
TextView tvHumidité;
TextView tvVitesse;
EditText edtVille;


    int titre =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        edtVille = findViewById(R.id.edtVille);
        tvCompteur = findViewById(R.id.tvcompteur);
        btnOk = findViewById(R.id.btnOK);
        tvTempératur = findViewById(R.id.tvTempératur);
        tvVille = findViewById(R.id.tvVille);
        tvTempératuremin = findViewById(R.id.tvTempératuremin);
        tvTemperaturemax = findViewById(R.id.tvTemperaturemax);
        tvHumidité = findViewById(R.id.tvHumidité);
        tvVitesse = findViewById(R.id.tvVitesse);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // methodeLongue();
               // titre += 1;
                setTitle("lematte");
                Thread thread = new Thread() {
                    public void run() {
                        getMeteo(edtVille.getText().toString());
                    }
                };
                thread.start();


            }
        });
    }

    private void methodeLongue() {
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.sleep(2000);
                tvCompteur.setText("" + i);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCompteur.setText("Bonjour");
            }
        });

    }

    private void getMeteo(String ville){
        try {

            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                    ville+ "&units=metric&APPID=5b1608b9d54c6fa1d411755e56700753");
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String text = "";
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
            is.close();
            System.out.println("text : " + text);
            JSONObject json = new JSONObject(text);

            System.out.println("Ville : " + json.getString("name"));
            JSONObject mainMeteo = json.getJSONObject("main");
            System.out.println("Température C : " + mainMeteo.getString("temp"));
            System.out.println("Température C (min) : " + mainMeteo.getString("temp_min"));
            System.out.println("Température C (max) : " + mainMeteo.getString("temp_max"));
            System.out.println("Humidité : " + mainMeteo.getString("humidity"));
            JSONObject windMeteo = json.getJSONObject("wind");
            System.out.println("Vitesse du vent (m/s) : " + windMeteo.getString("speed"));

           tvVille.setText( json.getString("name"));
            tvTempératur.setText( mainMeteo.getString("temp"));
            tvTempératuremin.setText( mainMeteo.getString("temp_min"));
            tvTemperaturemax.setText( mainMeteo.getString("temp_max"));
            tvHumidité.setText(mainMeteo.getString("humidity"));
            tvVitesse.setText( windMeteo.getString("speed"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    }