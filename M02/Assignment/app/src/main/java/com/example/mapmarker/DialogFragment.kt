package com.example.mapmarker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_dialog.*


class MyDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_enter.setOnClickListener {
            val latString = et_latitude.text.toString()
            val lonString = et_longitude.text.toString()
            val lat = latString.toDouble()
            val lon = lonString.toDouble()
            val newLatLng = LatLng(lat, lon)
            fragmentListener?.getLatLng(newLatLng)
            dialog?.dismiss()
        }

        button_cancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    interface FragmentListener {
        fun getLatLng(latLng: LatLng)
    }

    private var fragmentListener: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }
}
