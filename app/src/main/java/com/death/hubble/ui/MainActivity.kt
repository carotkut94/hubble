package com.death.hubble.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.death.hubble.R
import com.death.hubble.domain.NasaImageRepository
import com.death.hubble.domain.NasaImageRepositoryImpl
import com.death.hubble.util.common.BaseActivity
import com.death.hubble.util.common.ViewModelProviderFactory
import com.death.hubble.util.rx.RxSchedulerProviderImpl
import com.death.hubble.util.rx.SchedulerProvider
import org.koin.android.ext.android.get

class MainActivity : BaseActivity() {

    override val layoutRes = R.layout.activity_main

    private val nasaImageRepository:NasaImageRepositoryImpl = get()
    private val schedulerProvider:RxSchedulerProviderImpl = get()

    lateinit var mainViewModel: MainViewModel
    override fun setupView(savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class){
            MainViewModel(nasaImageRepository, compositeDisposable, schedulerProvider)
        }).get(MainViewModel::class.java)

        mainViewModel.loadPhotos()
    }

}
