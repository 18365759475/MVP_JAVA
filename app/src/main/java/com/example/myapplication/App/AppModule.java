package com.example.myapplication.App;

import android.app.Application;

import com.example.myapplication.mvp.RxBus;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class})
abstract class AppModule {

    @Binds
    @Singleton
    abstract Application application(BaseApplication baseApplication);

    @Provides
    @Singleton
    static RxBus rxBus() {
        return new RxBus();
    }
}
