package com.demo.swiperefreshlayout.ui

interface FragmentConnectivityListener {

    fun onInternetStatusChange(available: Boolean)

    fun onRefresh(automatic: Boolean)

}