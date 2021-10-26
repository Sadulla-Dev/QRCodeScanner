package com.intentsoft.qrcodescanner.ui.fragment


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.intentsoft.qrcodescanner.R
import com.intentsoft.qrcodescanner.db.DbHelper
import com.intentsoft.qrcodescanner.db.DbHelperI
import com.intentsoft.qrcodescanner.db.database.QrResultDataBase
import com.intentsoft.qrcodescanner.ui.dialog.QrCodeResultDialog
import kotlinx.android.synthetic.main.fragment_scanner.*
import kotlinx.android.synthetic.main.fragment_scanner.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class QRScannerFragment : Fragment(), ZBarScannerView.ResultHandler {


    companion object {
        fun newInstance(): QRScannerFragment {
            return QRScannerFragment()
        }
    }

    private lateinit var mView: View
    lateinit var resultDialog: QrCodeResultDialog
    private lateinit var dbHelperI: DbHelperI
    lateinit var scannerView: ZBarScannerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_scanner, container, false)
        onClicks()
        initView()
        init()
        return mView.rootView

    }

    private fun init() {
        dbHelperI = DbHelper(QrResultDataBase.getAppDatabase(context!!)!!)
    }


    private fun initView() {
        initializeQRCamera()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(context!!)
        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener {
            override fun onDismiss() {
                scannerView.resumeCameraPreview(this@QRScannerFragment)
            }

        })
    }


    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    private fun onQrResult(contents: String?) {
        if (contents.isNullOrEmpty())
            showToast("Empty Qr Result")
        else
            saveToDataBase(contents)
    }

    private fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveToDataBase(contents: String) {
        val insertedResultId = dbHelperI.insertQRResult(contents)
        val qrResult = dbHelperI.getQRResult(insertedResultId)
        resultDialog.show(qrResult)
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
    }
}
