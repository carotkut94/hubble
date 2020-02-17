package com.death.hubble

import android.app.Application
import com.death.hubble.domain.NasaImageRepositoryImpl
import com.death.hubble.network.RetrofitClient
import com.death.hubble.util.rx.RxSchedulerProviderImpl
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class HubbleApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HubbleApplication)
            modules(applicationDependenciesModule)
        }
    }

}



val applicationDependenciesModule =  module{
    single {
        RetrofitClient.create(BuildConfig.BASE_URL)
    }
    single {
        NasaImageRepositoryImpl(get())
    }
    single {
        CompositeDisposable()
    }
    single {
        RxSchedulerProviderImpl()
    }
}
