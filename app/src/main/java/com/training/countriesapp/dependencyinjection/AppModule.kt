package com.training.countriesapp.dependencyinjection

import com.training.countriesapp.repo.Repository
import com.training.countriesapp.repo.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

   @Singleton
   @Provides
   fun injectRepositoryInterface() = Repository() as RepositoryInterface
}