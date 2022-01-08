package com.fatihbaserpl.myartbookhilt.repo

import androidx.lifecycle.LiveData
import com.fatihbaserpl.myartbookhilt.api.RetrofitAPI
import com.fatihbaserpl.myartbookhilt.datamodel.ImageResponse
import com.fatihbaserpl.myartbookhilt.roomdb.Art
import com.fatihbaserpl.myartbookhilt.roomdb.ArtDao
import com.fatihbaserpl.myartbookhilt.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor (
    private val artDao : ArtDao,
    private val retrofitApi : RetrofitAPI
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }


}