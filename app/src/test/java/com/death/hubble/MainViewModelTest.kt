package com.death.hubble

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.death.hubble.data.NasaImage
import com.death.hubble.domain.NasaImageRepository
import com.death.hubble.ui.MainViewModel
import com.death.hubble.util.common.Resource
import com.death.hubble.util.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()


    /**
     * Here mocking the nasa image repository to access getNasaPhotos
     */

    @Mock
    private lateinit var nasaImageRepository: NasaImageRepository

    /**
     * Viewmodel instance to get hold of live data that will be binded with the UI
     */
    lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var loadingObserver : Observer<Boolean>

    @Mock
    private lateinit var resultObserver : Observer<Resource<List<NasaImage>>>
    @Mock
    private lateinit var errorObserver : Observer<Resource<String>>

    /**
     * Test scheduler inorder to get hold of virtual concurrency with virtual passing time.
     */
    @Mock
    private lateinit var testScheduler: TestScheduler


    private lateinit var schedulerProvider: SchedulerProvider

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TestSchedulerProvider(testScheduler)
        mainViewModel = MainViewModel(nasaImageRepository, CompositeDisposable(), schedulerProvider)
        mainViewModel.loading.observeForever(loadingObserver)
        mainViewModel.result.observeForever(resultObserver)
        mainViewModel.error.observeForever(errorObserver)
    }

    /**
     * Test case for success instance with mock data
     */

    @Test
    fun onLoadPhotos_getCalled(){

        /**
         * Mock data to be returned when the getPhotos method from nasaImageRepository is called.
         */
        val response = listOf(
            NasaImage("","","","","","","",""),
            NasaImage("","","","","","","",""),
            NasaImage("","","","","","","",""),
            NasaImage("","","","","","","","")
           )

        Mockito.`when`(nasaImageRepository.getNasaPhotos(BuildConfig.TOKEN)).thenReturn(Single.just(response))

        mainViewModel.loadPhotos()
        testScheduler.triggerActions()
        assert(mainViewModel.loading.value == false)
        assert(mainViewModel.result.value!!.data!!.size==4)
        assert(mainViewModel.result.value!!.data!![0].media_type.isEmpty())
    }
}
