package com.intentsoft.qrcodescanner.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intentsoft.qrcodescanner.R
import com.intentsoft.qrcodescanner.db.DbHelperI
import com.intentsoft.qrcodescanner.db.entitles.QrResult
import com.intentsoft.qrcodescanner.ui.dialog.QrCodeResultDialog
import com.intentsoft.qrcodescanner.utils.gone
import com.intentsoft.qrcodescanner.utils.toFormattedDisplay
import com.intentsoft.qrcodescanner.utils.visible
import kotlinx.android.synthetic.main.layout_single_item_qr_result.view.*

class ScannedResultListAdapter(var dbHelperI: DbHelperI, var context: Context, private var listOfScannedResult: MutableList<QrResult>):RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {


    private var resultDialog = QrCodeResultDialog(context)

    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        fun bind(qrResult: QrResult,position:Int){
            view.result.text = qrResult.result
            view.tvTime.text = qrResult.calendar.toFormattedDisplay()
            setFavourite(qrResult.favourite)
            onClick(qrResult)
        }

        private fun onClick(qrResult: QrResult) {
            view.setOnClickListener {
                resultDialog.show(qrResult)
            }
        }

        private fun setFavourite(favourite: Boolean) {
            if (favourite){
                view.favouriteIcon.visible()
            }else{
                view.favouriteIcon.gone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_single_item_qr_result,parent,false))
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position],position)
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }
}