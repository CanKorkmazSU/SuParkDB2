package com.example.suparkdb2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface SuParkDao {
    @Query("Select * from users order by suID ASC")
    suspend fun getAllUsers(): Flow<List<Users>>

    @Query("Select * from users where suID= :suid")
    suspend fun getUserbySuid(suid: Int): Flow<Users>

    @Query("Select * from cars")
    suspend fun getAllCars(): Flow<List<Cars>>

    @Query("Select * from cars where plateNo= :plateno")
    suspend fun getCarbyPlate(plateno: String): Flow<ParkingAreas>

    @Query("Select * from cars where brand= :brand")
    suspend fun getCarsbyBrand(brand: String): Flow<List<ParkingAreas>>

    @Query("Select * from parking_areas")
    suspend fun getAllPAreas(): Flow<List<ParkingAreas>>


    // get list of all owners with their cars
    @Transaction
    @Query("SELECT * FROM users")
    fun getOwnersWithCars(): Flow<List<OwnerWithCars>> // Room's way of using object reference-ish queries

    @Query("Select * From users where users.SuId = :ownerSuId") // get an owner and his/her cars
    fun loadOwnerAndCars(ownerSuId: Int): Flow<OwnerWithCars>



    @Query("Select * from used_by") // get all user-car combinations
    suspend fun getALlUsersWithCars(): Flow<List<UsedBy>>

    @Query("Select * from used_by where userSuId = :userSuId") // get a single user-car combinations for a single user with unique id (primary key)
    suspend fun getSingleUserWithCarsComb(userSuId: Int): Flow<List<UsedBy>> // !!! this query is unnecessary in production release !!!

    @Query("Select * from cars where  cars.cid in (Select cid from used_by where ownerSuId = :userSuId and cars.cid = used_by.cid)") // get a single  combinations for a single user with unique id (primary key)
    suspend fun getCarsofASingleUser(userSuId: Int): Flow<List<Cars>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: Users)

    @Insert
    fun addCar(car: Cars)

    @Insert
    fun addParkingArea(pa: ParkingAreas)

    @Insert
    fun addUsedBy(usedBy: UsedBy)

    @Insert
    fun addParkedBy(parkedBy: ParkedBy)
}