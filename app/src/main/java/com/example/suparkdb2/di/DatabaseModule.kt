package com.example.suparkdb2.di

import android.content.Context
import androidx.room.Room
import com.example.suparkdb2.data.SuParkDao
import com.example.suparkdb2.data.SuParkDatabase
import com.example.suparkdb2.data.SuParkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SuParkDatabase::class.java,
        "SuParkDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideDAO(database: SuParkDatabase) = database.suParkDao()

}
/*
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepo(
        dao: SuParkDao
    ) = SuParkRepository(dao)
}
*/
