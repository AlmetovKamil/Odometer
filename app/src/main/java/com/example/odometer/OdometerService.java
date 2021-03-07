package com.example.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.content.ContextCompat;

import java.util.Random;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private final Random random = new Random();
    private LocationListener listener;
    private LocationManager locManager;
    //Строка разрешения в виде константы
    public static final String PERMISSION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    //Расстояние и последнее местонахождение пользователя хранится
    // в статических переменных, чтобы их значения сохранялись при уничтожении службы
    private static double distance;
    private static Location lastLocation = null;

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
                if (lastLocation == null) {
                    //Задает исходное местонахождение пользователя
                    lastLocation = location;
                }
                //Обновляет пройденное расстояние и последнее местонахождение пользователя
                distance += location.distanceTo(lastLocation);
                lastLocation = location;
            }

            //Эти медоты не используются в коде, но они должны быть объявлены
            @Override
            public void onProviderDisabled(String arg0) {}
            @Override
            public void onProviderEnabled(String arg0) {}
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle bundle) {}
        };
        locManager = (LocationManager) getSystemService (Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
                == PackageManager.PERMISSION_GRANTED) {
            String provider = locManager.getBestProvider(new Criteria(), true);
            if (provider != null) {
                locManager.requestLocationUpdates(provider, 1000, 1, listener);
            }
        }
    }

    //Метод onBind() возвращает IBinder — интерфейс, реализуемый классом Binder
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //метод getDistance() пока что возвращает случайное число
    public double getDistance() {
        //return random.nextDouble();
        return distance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locManager != null && listener != null) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
                    == PackageManager.PERMISSION_GRANTED) {
                locManager.removeUpdates(listener);
            }
            locManager = null;
            listener = null;
        }
    }
}