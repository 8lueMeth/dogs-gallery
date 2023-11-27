package com.example.dogbreeds.data

import com.example.dogbreeds.database.FavBreedEntity
import com.example.dogbreeds.database.FavBreedsDao
import com.example.dogbreeds.di.IODispatcher
import com.example.dogbreeds.model.FavBreed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavBreedsRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val favBreedsDao: FavBreedsDao,
) : FavBreedsRepository {

    override fun getAllFavBreeds(): Flow<List<FavBreed>> =
        favBreedsDao.getAllFavBreeds().map { list ->
            list.map { entity ->
                FavBreed(
                    name = entity.name,
                    subBreedName = entity.subBreedName,
                    imageId = entity.imageId,
                    imageUrl = entity.imageUrl
                )
            }
        }

    override fun filterFavBreeds(favBreed: FavBreed): Flow<List<FavBreed>> =
        favBreedsDao.getAllFavByBreed(name = favBreed.name, favBreed.subBreedName).map { list ->
            list.map { entity ->
                FavBreed(
                    name = entity.name,
                    subBreedName = entity.subBreedName,
                    imageId = entity.imageId,
                    imageUrl = entity.imageUrl
                )
            }
        }

    override suspend fun updateFavorites(favBreed: FavBreed, isLiked: Boolean) {
        withContext(dispatcher) {
            if (isLiked) {
                val entity = FavBreedEntity(
                    imageId = favBreed.imageId,
                    name = favBreed.name,
                    subBreedName = favBreed.subBreedName,
                    imageUrl = favBreed.imageUrl
                )
                favBreedsDao.insertFavBreed(entity)
            } else {
                favBreedsDao.deleteFavBreed(favBreed.imageId)
            }
        }
    }
}