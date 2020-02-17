package com.death.hubble.network

import com.death.hubble.data.NasaImage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkService{

    @GET(Endpoints.TRENDING_REPOS)
    fun getTrendingRepositories(@Header("token") token:String): Single<List<NasaImage>>

}