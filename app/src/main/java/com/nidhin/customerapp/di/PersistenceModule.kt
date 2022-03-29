package com.nidhin.customerapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.nidhin.customerapp.persistance.AppDatabase
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPrefsHelper.PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context,
        @Named("databaseName") dbName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Named("databaseName")
    fun provideDatabaseName(): String = "silence.db"

}