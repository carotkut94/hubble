package com.death.hubble.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.death.hubble.R
import com.death.hubble.databinding.InfoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoDialogBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(info: String) = InfoDialogBottomSheet().apply {
            arguments = Bundle().apply {
                putString("info", info)
            }
        }
    }

    private lateinit var binding:InfoBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.info_bottom_sheet, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.infoText.text = arguments!!.getString("info")
    }

}