package com.waleska404.shrek.di

import android.content.Context
import androidx.room.Room
import com.waleska404.shrek.data.local.ShrekDatabase
import com.waleska404.shrek.data.repository.LocalDataSourceImpl
import com.waleska404.shrek.domain.repository.LocalDataSource
import com.waleska404.shrek.util.Constants.SHREK_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShrekDatabase::class.java,
        SHREK_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: ShrekDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            shrekDatabase = database
        )
    }
}