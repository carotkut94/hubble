package com.death.hubble.network

import com.death.hubble.data.NasaImage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkService{

    /**
     * This interface method when overriden needs token as the arguments and returns the
     * list of nasa images
     */
    @GET(Endpoints.TRENDING_REPOS)
    fun getNasaPhotos(@Header("token") token:String): Single<List<NasaImage>>

}