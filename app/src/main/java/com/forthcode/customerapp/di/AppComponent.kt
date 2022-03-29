package com.forthcode.customerapp.di

import android.content.Context
import com.forthcode.customerapp.BaseApplication
import com.forthcode.customerapp.di.modules.ActivityBuilderModule
import com.forthcode.customerapp.di.modules.ApplicationModule
import com.forthcode.customerapp.di.modules.ApiModule
import com.forthcode.customerapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ApiModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(app: BaseApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}
