package com.death.hubble.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.death.hubble.R
import com.death.hubble.data.NasaImage
import com.death.hubble.databinding.ActivityMainBinding
import com.death.hubble.domain.NasaImageRepositoryImpl
import com.death.hubble.ui.adapter.NasaImageAdapter
import com.death.hubble.ui.detail.NasaImageDetailActivity
import com.death.hubble.util.common.BaseActivity
import com.death.hubble.util.common.ItemDecoration
import com.death.hubble.util.common.ViewModelProviderFactory
import com.death.hubble.util.rx.RxSchedulerProviderImpl
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : BaseActivity<ActivityMainBinding>(), NasaImageAdapter.ClickListener {

    override val layoutRes = R.layout.activity_main

    private val nasaImageRepository: NasaImageRepositoryImpl = get()
    private val schedulerProvider: RxSchedulerProviderImpl = get()

    private lateinit var mainViewModel: MainViewModel
    private val nasaImageList = ArrayList<NasaImage>()
    private lateinit var nasaImageAdapter: NasaImageAdapter

    override fun setupView(savedInstanceState: Bundle?) {


        mainViewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(nasaImageRepository, compositeDisposable, schedulerProvider)
        }).get(MainViewModel::class.java)

        binding.vm = mainViewModel


        nasaImageAdapter = NasaImageAdapter(nasaImageList, this)

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

    override fun onClick(
        nasaImage: NasaImage,
        posterImageView: ImageView,
        headline: TextView,
        posterTransitionName: String,
        headlineTransitionName: String
    ) {
        Log.e("Data", nasaImage.toString())
        val intent = Intent(this@MainActivity, NasaImageDetailActivity::class.java)
        intent.putExtra("data", nasaImage)
        val photoSharedElement: Pair<View, String> = Pair.create(
            posterImageView,
            posterTransitionName
        )
        val titleSharedElement: Pair<View, String> = Pair.create(
            headline,
            headlineTransitionName
        )
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity,
            photoSharedElement,
            titleSharedElement
        )
        startActivity(intent, options.toBundle())
    }

}
