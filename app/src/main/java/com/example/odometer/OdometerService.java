package com.example.odometer;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.util.Random;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private final Random random = new Random();
    private LocationListener listener;

    //При создании связанной службы необходимо предоставить реализацию Binder
    public class OdometerBinder extends Binder {

        //Метод используется активностью для получения ссылки на OdometerService
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    public OdometerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Отслеживание расстояния, пройденного пользователем
            }

            //Эти медоты не используются в коде, но они должны быть объявлены
            @Override
            public void onProviderDisabled(String arg0) {}
            @Override
            public void onProviderEnabled(String arg0) {}
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle bundle) {}
        };
    }

    //Метод onBind() возвращает IBinder — интерфейс, реализуемый классом Binder
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //метод getDistance() пока что возвращает случайное число
    public double getDistance() {
        return random.nextDouble();
    }
}