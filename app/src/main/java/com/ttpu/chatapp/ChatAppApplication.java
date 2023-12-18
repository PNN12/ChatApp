package com.ttpu.chatapp;

import android.app.Application;

@SuppressWarnings("ALL")
public class ChatAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Здесь можно произвести инициализацию глобальных объектов

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Здесь можно произвести освобождение ресурсов при завершении работы приложения
    }
}

