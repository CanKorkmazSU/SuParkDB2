package com.example.suparkdb2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface SuParkDao {
    @Query("Select * from users order by suID ASC")
     fun getAllUsers(): Flow<List<Users>>

    @Query("Select * from users where suID= :suid")
     fun getUserbySuid(suid: Int): Users?

    @Query("Select * from cars")
     fun getAllCars(): Flow<List<Cars>>

    @Query("Select * from cars where plateNo= :plateno")
     fun getCarbyPlate(plateno: String): Flow<Cars>

    @Query("Select * from cars where cid= :cid")
     fun getCarbyCid(cid: Int): Flow<Cars>


    @Query("Select * from cars where brand= :brand")
     fun getCarsbyBrand(brand: String): Flow<List<Cars>>

    @Query("Select * from parking_areas")
     fun getAllPAreas(): Flow<List<ParkingAreas>>

    @Query("Select parkId from parked_by where cid = :carId")
     fun getLocationByCarId(carId: Int): Flow<Int>

    @Query("Select * from parking_areas where pid =:parkId")
     fun getParkingAreaByParkId(parkId: Int): Flow<ParkingAreas>

    // get list of all owners with their cars
    @Transaction
    @Query("SELECT * FROM users")
    fun getOwnersWithCars(): Flow<List<OwnerWithCars>> // Room's way of using object reference-ish queries

    @Query("Select * From users where users.SuId = :ownerSuId") // get an owner and his/her cars
    fun loadOwnerAndCars(ownerSuId: Int): Flow<OwnerWithCars>



    @Query("Select * from used_by") // get all user-car combinations
     fun getALlUsersWithCars(): Flow<List<UsedBy>>

    @Query("Select * from used_by where userSuId = :userSuId") // get a single user-car combinations for a single user with unique id (primary key)
     fun getSingleUserWithCarsComb(userSuId: Int): Flow<List<UsedBy>> // !!! this query is unnecessary in production release !!!

     @Transaction
    @Query("Select * from cars where  cars.cid in (Select cid from used_by where ownerSuId = :userSuId " +
            "and cars.cid = used_by.cid)") // get a single  combinations for a single user with unique id (primary key)
     fun getCarsofASingleUser(userSuId: Int): Flow<List<Cars>>

    @Query("Select * from parked_by where leavingDateTime is null and cid in (Select cid From used_by where suID = :userSuId) ") // get all cars that are also used or owned by userSuId that are parked in SuCampus
     fun getAllParkedCarsBySuId(userSuId: Int): Flow<List<ParkedBy>>

    @Query("Select* from cars where cid in (select cid from parked_by where leavingDateTime is null )")
     fun getAllParkedCars(): Flow<List<Cars>>

    @Query("Select * from parking_areas where pid in (Select parkId from parked_by where leavingDateTime is null and cid = :cid ) ")
     fun getActiveParkingAreaForCar(cid: Int): Flow<ParkingAreas>



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


    //@Delete(entity = Users::class)
    //fun removeUserBySuId(suId: Int)


}