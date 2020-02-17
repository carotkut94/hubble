package com.death.hubble.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.death.hubble.data.NasaImage
import com.death.hubble.databinding.CardNasaPhotoBinding
import com.squareup.picasso.Picasso


class NasaImageAdapter(
    private val nasaImages: List<NasaImage>
) :
    RecyclerView.Adapter<NasaImageAdapter.NasaImageViewHolder>() {

    lateinit var cardNasaPhotoBinding: CardNasaPhotoBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NasaImageViewHolder {
        cardNasaPhotoBinding = CardNasaPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NasaImageViewHolder(cardNasaPhotoBinding.root)
    }

    override fun onBindViewHolder(
        holder: NasaImageViewHolder,
        position: Int
    ) {
        holder.bindData(nasaImages[position])
    }


    override fun getItemCount(): Int {
        return nasaImages.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class NasaImageViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindData(nasaImage: NasaImage) {
            cardNasaPhotoBinding.headline.text = nasaImage.title
            Picasso.get()
                .load(nasaImage.url)
                .into(cardNasaPhotoBinding.poster)

            cardNasaPhotoBinding.headline.setBackgroundColor(Color.BLACK)
            cardNasaPhotoBinding.headline.setTextColor(Color.WHITE)

        }
    }

    fun getComplimentColor(color: Int): Int {
        val alpha = Color.alpha(color)
        var red = Color.red(color)
        var blue = Color.blue(color)
        var green = Color.green(color)
        red = red.inv() and 0xff
        blue = blue.inv() and 0xff
        green = green.inv() and 0xff
        return Color.argb(alpha, red, green, blue)
    }

    interface ClickListener {
        fun onClick(view: View?, position: Int)
    }

}
