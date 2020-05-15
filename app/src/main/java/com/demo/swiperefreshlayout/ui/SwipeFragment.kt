package com.demo.swiperefreshlayout.ui

import android.content.Context
import android.net.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.demo.swiperefreshlayout.R
import kotlinx.android.synthetic.main.fragment_swipe.*

/**
 * A simple [Fragment] subclass.
 */
class SwipeFragment : Fragment() {


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        private val handler = Handler(Looper.getMainLooper())

        override fun onLost(network: Network) {
            handler.post { setInternetAvailable(false) }
        }

        override fun onAvailable(network: Network) {
            handler.post { setInternetAvailable(true) }
        }
    }

    private val connectivityManager by lazy {
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container, ContentFragment())
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )

        layout_swipe.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
            setOnRefreshListener {
                getConnectivityListener()?.onRefresh(false)
            }
        }
    }

    override fun onDestroyView() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onDestroyView()
    }

    private fun getConnectivityListener(): FragmentConnectivityListener? {
        return (childFragmentManager.findFragmentById(R.id.child_fragment_container) as? FragmentConnectivityListener)
    }

    fun showProgress(){
        layout_swipe.isRefreshing = true
    }

    fun hideProgress(){
        layout_swipe.isRefreshing = false
    }

    fun setInternetAvailable(available: Boolean) {
        if (view == null) return

        getConnectivityListener()?.onInternetStatusChange(available)

        if (available) {
            text_no_internet.visibility = View.GONE
            getConnectivityListener()?.onRefresh(true)
        } else {
            text_no_internet.visibility = View.VISIBLE
        }
    }

}
