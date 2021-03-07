package com.example.odometer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private final Random random = new Random();

    //При создании связанной службы необходимо предоставить реализацию Binder
    public class OdometerBinder extends Binder {

        //Метод используется активностью для получения ссылки на OdometerService
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    public OdometerService() {
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