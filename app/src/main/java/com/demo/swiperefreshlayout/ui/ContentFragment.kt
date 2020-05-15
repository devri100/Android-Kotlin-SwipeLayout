package com.demo.swiperefreshlayout.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.demo.swiperefreshlayout.R
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.fragment_swipe.*

/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : Fragment(), FragmentConnectivityListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onInternetStatusChange(available: Boolean) {
        if (available) {
            text.text = "Van internet"
        } else {
            text.text = "Nincs internet"
        }
    }

    override fun onRefresh(automatic: Boolean) {
        (parentFragment as SwipeFragment).showProgress()
        Thread {
            Thread.sleep(2000)
            Handler(Looper.getMainLooper()).post {
                (parentFragment as SwipeFragment).hideProgress()
            }
        }.start()
    }
}
