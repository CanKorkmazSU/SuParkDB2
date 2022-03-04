package com.example.suparkdb2.data

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.concurrent.Flow
import javax.inject.Inject

@ViewModelScoped
class SuParkRepository @Inject constructor(private val suParkDao: SuParkDao) {
    fun getAllUsers() = suParkDao.getAllUsers()
    fun getAllCars() = suParkDao.getAllCars()
    fun getAllPAreas() = suParkDao.getAllPAreas()
    fun getUserbySUid(suid: Int): Users? = suParkDao.getUserbySuid(suid)
    fun getCarbyPlate(plateno: String) = suParkDao.getCarbyPlate(plateno)
    fun getCarsByCarId(cid: Int) = suParkDao.getCarbyCid(cid)
    fun getCarsbyBrand(brand: String) = suParkDao.getCarsbyBrand(brand)
    fun getLocationByCarId(carId: Int) = suParkDao.getLocationByCarId(carId)
    fun getParkingAreaByPid(parkId: Int) = suParkDao.getParkingAreaByParkId(parkId)
    fun getAllParkedCarsBySuId(suId: Int) = suParkDao.getAllParkedCarsBySuId(suId)

    fun getAllParkedCars() =suParkDao.getAllParkedCars()
    fun getActiveParkingAreaForCar(cid: Int) = suParkDao.getActiveParkingAreaForCar(cid)
    fun getCarsOfASingleUser(suId: Int) = suParkDao.getCarsofASingleUser(suId)


    fun addUser(user: Users) = suParkDao.addUser(user)
    fun addCar(car: Cars) = suParkDao.addCar(car)
    fun addParkingArea(pa: ParkingAreas) = suParkDao.addParkingArea(pa)

    fun addParkedBy(parkedBy: ParkedBy) = suParkDao.addParkedBy(parkedBy)
}