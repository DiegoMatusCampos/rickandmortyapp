package com.example.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.BuildConfig
import com.example.rickandmorty.data.database.character.CharacterDao
import com.example.rickandmorty.data.database.RickAndMortyDatabase
import com.example.rickandmorty.data.network.ApiService
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

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RickAndMortyDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = RickAndMortyDatabase::class.java,
            name = "rickandmorty.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideCharacterDao(db: RickAndMortyDatabase): CharacterDao =  db.characterDao()

}