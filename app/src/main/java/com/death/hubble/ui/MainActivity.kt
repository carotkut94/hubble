package com.death.hubble.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.death.hubble.R
import com.death.hubble.data.NasaImage
import com.death.hubble.databinding.ActivityMainBinding
import com.death.hubble.domain.NasaImageRepositoryImpl
import com.death.hubble.ui.adapter.NasaImageAdapter
import com.death.hubble.util.common.BaseActivity
import com.death.hubble.util.common.ItemDecoration
import com.death.hubble.util.common.ViewModelProviderFactory
import com.death.hubble.util.rx.RxSchedulerProviderImpl
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutRes = R.layout.activity_main

    private val nasaImageRepository: NasaImageRepositoryImpl = get()
    private val schedulerProvider: RxSchedulerProviderImpl = get()

    lateinit var mainViewModel: MainViewModel
    private val nasaImageList = ArrayList<NasaImage>()
    lateinit var nasaImageAdapter: NasaImageAdapter

    override fun setupView(savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(nasaImageRepository, compositeDisposable, schedulerProvider)
        }).get(MainViewModel::class.java)

        binding.vm = mainViewModel


        nasaImageAdapter = NasaImageAdapter(nasaImageList)

        images.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = nasaImageAdapter
            addItemDecoration(ItemDecoration(20))
        }

        mainViewModel.result.observe(this, Observer {
            if (it.data!!.isNotEmpty()) {
                nasaImageList.addAll(it.data)
                nasaImageAdapter.notifyDataSetChanged()
            }
        })

        mainViewModel.error.observe(this, Observer {
            showMessage(it.data!!)
        })


        mainViewModel.loadPhotos()

    }

}
