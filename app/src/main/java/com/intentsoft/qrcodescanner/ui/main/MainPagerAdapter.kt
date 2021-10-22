package com.intentsoft.qrcodescanner.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.intentsoft.qrcodescanner.ui.fragment.QRScannerFragment
import com.intentsoft.qrcodescanner.ui.fragment.ScannerHistoryFragment

class MainPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> QRScannerFragment.newInstance()
            1 -> ScannerHistoryFragment.newInstance(ScannerHistoryFragment.ResultListType.ALL_RESULT)
            2 -> ScannerHistoryFragment.newInstance(ScannerHistoryFragment.ResultListType.FAVOURITE_RESULT)
            else -> QRScannerFragment.newInstance()
        }
    }
}