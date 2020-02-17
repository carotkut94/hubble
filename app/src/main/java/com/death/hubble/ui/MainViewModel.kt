package com.death.hubble.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.death.hubble.BuildConfig
import com.death.hubble.data.NasaImage
import com.death.hubble.domain.NasaImageRepository
import com.death.hubble.util.common.Resource
import com.death.hubble.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val nasaImageRepository: NasaImageRepository,private val compositeDisposable: CompositeDisposable, private val schedulerProvider: SchedulerProvider): ViewModel() {

    val loading:MutableLiveData<Boolean> = MutableLiveData(false)
    val result:MutableLiveData<Resource<List<NasaImage>>> = MutableLiveData()
    val error:MutableLiveData<Resource<String>> = MutableLiveData()

    fun loadPhotos(){
        loading.postValue(true)
        compositeDisposable.add(
            nasaImageRepository.getNasaPhotos(BuildConfig.TOKEN)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
            .subscribe({
                loading.postValue(false)
                result.postValue(Resource.success(it.data))
            },{
                it.printStackTrace()
                error.postValue(Resource.error("Something is not right"))
                loading.postValue(false)
            }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}