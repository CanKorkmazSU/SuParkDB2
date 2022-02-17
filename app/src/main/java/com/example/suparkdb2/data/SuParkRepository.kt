package com.example.suparkdb2.data

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.concurrent.Flow
import javax.inject.Inject

@ViewModelScoped
class SuParkRepository @Inject constructor(private val suParkDao: SuParkDao) {
    suspend fun getAllUsers() = suParkDao.getAllUsers()
    suspend fun getAllCars() = suParkDao.getAllCars()
    suspend fun getAllPAreas() = suParkDao.getAllPAreas()
    suspend fun getUserbySUid(suid: Int) = suParkDao.getUserbySuid(suid)
    suspend fun getCarbyPlate(plateno: String) = suParkDao.getCarbyPlate(plateno)
    suspend fun getCarsByCarId(cid: Int) = suParkDao.getCarbyCid(cid)
    suspend fun getCarsbyBrand(brand: String) = suParkDao.getCarsbyBrand(brand)
    suspend fun getLocationByCarId(carId: Int) = suParkDao.getLocationByCarId(carId)
    suspend fun getParkingAreaByPid(parkId: Int) = suParkDao.getParkingAreaByParkId(parkId)
    suspend fun getAllParkedCarsBySuId(suId: Int) = suParkDao.getAllParkedCarsBySuId(suId)

    fun addUser(user: Users) = suParkDao.addUser(user)
    fun addCar(car: Cars) = suParkDao.addCar(car)
    fun addParkingArea(pa: ParkingAreas) = suParkDao.addParkingArea(pa)

    fun addParkedBy(parkedBy: ParkedBy) = suParkDao.addParkedBy(parkedBy)
}