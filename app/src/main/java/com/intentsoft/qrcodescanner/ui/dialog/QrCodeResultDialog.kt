package com.intentsoft.qrcodescanner.ui.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.intentsoft.qrcodescanner.R
import com.intentsoft.qrcodescanner.db.DbHelper
import com.intentsoft.qrcodescanner.db.DbHelperI
import com.intentsoft.qrcodescanner.db.database.QrResultDataBase
import com.intentsoft.qrcodescanner.db.entitles.QrResult
import com.intentsoft.qrcodescanner.utils.toFormattedDisplay
import kotlinx.android.synthetic.main.layout_qr_reslut_show.*

class QrCodeResultDialog(var context: Context) {
    private lateinit var dialog: Dialog
    private var onDismissListener: OnDismissListener? = null
    private var qrResult: QrResult? = null
    private lateinit var dbHelperI: DbHelperI


    init {
        init()
        initDialog()
    }

    private fun init() {
        dbHelperI = DbHelper(QrResultDataBase.getAppDatabase(context)!!)
    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_qr_reslut_show)
        dialog.setCancelable(false)
        onClick()
    }
    fun setOnDismissListener(dismissListener: OnDismissListener) {
        this.onDismissListener = dismissListener
    }

    fun show(qrResult: QrResult){
        this.qrResult = qrResult
        dialog.scannedDate.text = qrResult?.calendar.toFormattedDisplay()
        dialog.scannedText.text = qrResult.result
        dialog.favouriteIcon.isSelected = qrResult.favourite
        dialog.show()
    }

    private fun onClick() {
        dialog.favouriteIcon.setOnClickListener {
            if (it.isSelected) {
                removeFromFavourite()
            } else
                addToFavourite()
        }

        dialog.shareResult.setOnClickListener {
            shareResult()
        }

        dialog.copyResult.setOnClickListener {
            copyReslutClipBoard()
        }

        dialog.cancelDialog.setOnClickListener {
            onDismissListener?.onDismiss()
            dialog.dismiss()
        }
    }

    private fun addToFavourite() {
        dialog.favouriteIcon.isSelected = true
        dbHelperI.addToFavourite(qrResult?.id!!)
    }

    private fun removeFromFavourite() {
        dialog.favouriteIcon.isSelected = false
        dbHelperI.removeFromFavourite(qrResult?.id!!)
    }

    private fun shareResult() {
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(Intent.EXTRA_TEXT, dialog.scannedText.text.toString())
        context.startActivity(Intent.createChooser(txtIntent, "Share QR Result"))
    }

    private fun copyReslutClipBoard() {
        val clipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("QrCodeScannedResult", dialog.scannedText.text)
        clipBoard.text = clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
    interface OnDismissListener {
        fun onDismiss()
    }
}