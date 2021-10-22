package com.intentsoft.qrcodescanner.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.intentsoft.qrcodescanner.R
import com.intentsoft.qrcodescanner.db.database.QrResultDataBase
import com.intentsoft.qrcodescanner.db.entitles.QrResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPagerAdapter()
        setBottomNavigation()
        setViewPagerListener()

        var qrResult = QrResult(result = "Dummy text",resultType = "TEXT",favourite = false,calendar = Calendar.getInstance())
        QrResultDataBase.getAppDatabase(this)?.getQrDao()?.insertQrResult(qrResult)
    }

    private fun setBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.qrScanMenuId -> {
                    viewPager.currentItem = 0
                }
                R.id.scannedResultMenuId -> {
                    viewPager.currentItem = 1

                }
                R.id.favouriteScannedMenuId -> {
                    viewPager.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerAdapter() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
    }
    private fun setViewPagerListener() {
        viewPager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.qrScanMenuId
                    }
                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.scannedResultMenuId
                    }
                    2 -> {
                        bottomNavigationView.selectedItemId = R.id.favouriteScannedMenuId
                    }
                }
            }
        })
    }
}