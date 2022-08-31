package com.nasa.gallary.domain.use_cases

import com.nasa.gallary.app.common.Resource
import com.nasa.gallary.domain.repository.NasaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNasaDataUseCase constructor(
    private val repository: NasaRepository
) {
    fun getNasaData(forceNetwork : Boolean = false) = flow {
        emit(Resource.Loading)
        val repositories = repository.getNasaData(forceNetwork)
        emit(Resource.Success(repositories))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)
}