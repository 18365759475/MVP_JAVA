package com.example.myapplication.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class})
interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApplication> {

    }
}
