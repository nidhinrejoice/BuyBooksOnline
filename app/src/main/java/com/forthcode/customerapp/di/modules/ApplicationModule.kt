package com.forthcode.customerapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.forthcode.customerapp.persistance.SharedPrefsHelper
import com.forthcode.customerapp.persistance.db.InventoryRepoDao
import com.forthcode.customerapp.utils.AppExecutors
import com.forthcode.customerapp.utils.DiskIOThreadExecutor
import com.mvvmclean.trendingrepos.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * This is a Dagger provider module
 */
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPrefsHelper.PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideDb(context: Context, @Named("databaseName") dbName: String): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors(
            DiskIOThreadExecutor(),
            AppExecutors.MainThreadExecutor()
        )
    }

    @Provides
    @Named("databaseName")
    fun provideDatabaseName(): String = "silence.db"

    @Singleton
    @Provides
    fun provideInventoryRepoDao(appDatabase: AppDatabase): InventoryRepoDao =
        appDatabase.inventoryDao()
}