package com.example.suparkdb2.data

import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase


@Database(entities = [Users::class, Cars::class, ParkingAreas::class, ParkedBy::class, UsedBy::class, OwnerCarCrossRef::class], version = 1, exportSchema = false)
abstract  class SuParkDatabase: RoomDatabase() {
    abstract fun suParkDao(): SuParkDao
}