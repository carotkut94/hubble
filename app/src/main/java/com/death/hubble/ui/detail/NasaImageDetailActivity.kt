package com.death.hubble.ui.detail

import android.os.Bundle
import android.view.WindowManager
import com.death.hubble.R
import com.death.hubble.data.NasaImage
import com.death.hubble.databinding.ActivityNasaImageDetailBinding
import com.death.hubble.util.common.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_nasa_image_detail.*

class NasaImageDetailActivity : BaseActivity<ActivityNasaImageDetailBinding>() {

    override val layoutRes: Int = R.layout.activity_nasa_image_detail
    lateinit var nasaImage: NasaImage
    override fun setupView(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        nasaImage = intent.getParcelableExtra("data") as NasaImage
        Picasso.get().load(nasaImage.hdurl).placeholder(R.drawable.loading_background).into(poster)
        headline.text = nasaImage.title


        back.setOnClickListener {
            onBackPressed()
        }


        info.setOnClickListener {
            InfoDialogBottomSheet.newInstance(nasaImage.explanation).show(supportFragmentManager, "info")
        }
    }


}
