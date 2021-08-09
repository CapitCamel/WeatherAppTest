package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.ApiService
import com.example.weatherapp.data.db.AppDatabase
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.repository.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, database: AppDatabase) = Repository(apiService, database)

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        AppDatabase.getAppDatabase(context)

}