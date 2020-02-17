package com.death.hubble.domain

import com.death.hubble.data.NasaImage
import io.reactivex.Single

interface NasaImageRepository{
    fun getNasaPhotos(token:String): Single<List<NasaImage>>
}