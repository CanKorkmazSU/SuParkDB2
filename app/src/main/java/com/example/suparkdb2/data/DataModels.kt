package com.example.suparkdb2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

@Entity(tableName = "users", indices = [Index(value = [ "driversLicense" ], unique = true)])
data class Users(
    val name: String,
    val surname: String,
    val diversLicense: String,
    val age: Int,
    @PrimaryKey val suID: Int,
    val isOwner: Boolean,
    val isStudent: Boolean,
    val isPersonnel: Boolean,
    val isFacultyMember: Boolean,
    val isAdmin: Boolean,
    //@PrimaryKey(autoGenerate = true) val id: Int
)

@Entity(tableName = "cars", indices = [Index(value = [ "plateNo" ], unique = true)])
data class Cars(
    val ownerSuId: Int,
    val brand: String,
    val carModel: String,
    val plateNo: String,
    @PrimaryKey(autoGenerate = true) val cid:Int
)

@Entity(tableName = "used_by",foreignKeys = [
    ForeignKey(entity = Users::class, parentColumns = ["suID"], childColumns = ["userSuId"]),
    ForeignKey(entity = Cars::class, parentColumns = ["cid"], childColumns = ["cid"] )],
    primaryKeys = ["userSuId", "cid"]
)
data class UsedBy(
    val userSuId: Int,
    val cid: Int
)

@Entity(tableName = "parking_areas",indices = [Index(value = [ "parkName" ], unique = true)] )
data class ParkingAreas(
    val parkName: String,
    val capacity: Int,
    @PrimaryKey(autoGenerate = true) val pid:Int
)

@Entity(tableName = "parked_by", foreignKeys = [
    ForeignKey(entity = Users::class, parentColumns = ["suID"], childColumns = ["suID"]),
    ForeignKey(entity = Cars::class, parentColumns = ["cid"], childColumns = ["cid"] ),
    ForeignKey(entity = ParkingAreas::class, parentColumns = ["pid"], childColumns = ["parkId"])
])
data class ParkedBy(
    val suID: Int,
    val cid: Int,
    val parkId: Int,
    val entranceDateTime: String,// change Time to kotlin Duration, or maybe to string to use .toIsoString() and from iso string to Duration
    val leavingDateTime: String?,
    @PrimaryKey(autoGenerate = true) val parkedById: Int
)

@Entity(tableName = "car_sticker", foreignKeys = [
    ForeignKey(entity = Cars::class, parentColumns = ["cid"], childColumns = ["carId"]),
])
data class CarSticker(
    val carId: Int,
    val issueDate: Date, // maybe change this to String
    val dueDate: Date,
    @PrimaryKey(autoGenerate = true) val stickerNo: Int
)


