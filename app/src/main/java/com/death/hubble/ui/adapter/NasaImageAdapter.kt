package com.death.hubble.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.death.hubble.data.NasaImage
import com.death.hubble.databinding.CardNasaPhotoBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_nasa_photo.view.*


class NasaImageAdapter(
    private val nasaImages: List<NasaImage>,
    private val clickListener: ClickListener
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

        holder.itemView.setOnClickListener {
            clickListener.onClick(nasaImages[position], holder.itemView.poster, holder.itemView.headline, holder.itemView.poster.transitionName, holder.itemView.headline.transitionName)
        }
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


    interface ClickListener {
        fun onClick(
            nasaImage: NasaImage,
            posterImageView: ImageView,
            headline: TextView,
            posterTransitionName: String,
            headlineTransitionName: String
        )
    }

}
