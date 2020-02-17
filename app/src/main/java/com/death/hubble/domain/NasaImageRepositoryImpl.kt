package com.death.hubble.domain

import com.death.hubble.data.ApiResponse
import com.death.hubble.data.NasaImage
import com.death.hubble.network.NetworkService
import io.reactivex.Single



class NasaImageRepositoryImpl(private val networkService: NetworkService) : NasaImageRepository{
    override fun getNasaPhotos(token: String): Single<ApiResponse<List<NasaImage>>> = networkService.getNasaPhotos(token)
}