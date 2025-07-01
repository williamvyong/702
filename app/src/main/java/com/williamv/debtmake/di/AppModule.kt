package com.williamv.debtmake.di

import com.williamv.debtmake.data.GreetingService
import com.williamv.debtmake.data.GreetingServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindGreetingService(
        impl: GreetingServiceImpl
    ): GreetingService
}