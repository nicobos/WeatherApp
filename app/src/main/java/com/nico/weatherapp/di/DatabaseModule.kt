package com.nico.weatherapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nico.weatherapp.data.database.RoomDB
import com.nico.weatherapp.data.database.dao.WeatherDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    companion object {
        @Volatile
        private lateinit var localDatabase: RoomDB
        private const val DB_NAME = "WeatherDB"

        @Provides
        @Singleton
        fun provideRoomDB(application: Application): RoomDB {
            localDatabase =
                Room.databaseBuilder(application, RoomDB::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            localDatabase.let {
                                Timber.i("RoomDB: Opened")
                            }
                        }
                    })
                    .build()

            return localDatabase
        }

        @Provides
        fun providesWeatherDataDao(database: RoomDB): WeatherDataDao {
            return database.weatherDataDao()
        }

    }


}