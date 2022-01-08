package com.fatihbaserpl.myartbookhilt.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {// Coruteinlerle birlikte çalıştırdığımız gerektiğinde durdurup çalıştırdığımız devam edebilen
    //main threadde çalıştırmak istemiyoruz çünkü inser update delete yapıyoruz ağır oluyor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art:Art)

    @Delete
    suspend fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>

}