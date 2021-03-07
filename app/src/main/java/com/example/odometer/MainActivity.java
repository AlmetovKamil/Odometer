package com.example.odometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends AppCompatActivity {

    //Создание объекта ServiceConnection
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Код, выполняемый при связывании со службой
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //Код, выполняемый при разрыве связи со службой
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}