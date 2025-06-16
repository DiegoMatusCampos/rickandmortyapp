package com.example.rickandmorty.di

import com.example.rickandmorty.data.repository.CharacterRepositoryImpl
import com.example.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.repository.EpisodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        implement: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(
        impl: EpisodeRepositoryImpl
    ): EpisodeRepository
}