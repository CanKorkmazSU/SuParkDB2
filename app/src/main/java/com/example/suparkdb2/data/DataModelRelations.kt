package com.example.suparkdb2.data

import androidx.room.*

@Entity(primaryKeys = ["suID", "cid"])
data class OwnerCarCrossRef(
    val suID: Int,
    val ownerSuId: Int
)

data class OwnerWithCars( // one to many relationship
    @Embedded val user: Users,
    @Relation(
        parentColumn = "suID",
        entityColumn = "ownerSuId",
        associateBy = Junction(OwnerCarCrossRef::class)
    )
    val cars: List<Cars>
)


