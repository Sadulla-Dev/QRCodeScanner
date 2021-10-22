package com.intentsoft.qrcodescanner.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intentsoft.qrcodescanner.R
import java.io.Serializable


class ScannerHistoryFragment : Fragment() {
    private lateinit var resultType: ResultListType
    private lateinit var mView: View
    enum class ResultListType : Serializable {
        ALL_RESULT, FAVOURITE_RESULT
    }
    companion object {

        private const val ARGUMENT_RESULT_LIST_TYPE = "ArgumentResultType"

        fun newInstance(screenType: ResultListType): ScannerHistoryFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE, screenType)
            val fragment = ScannerHistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArgument()
    }

    private fun handleArgument() {
        resultType = arguments?.getSerializable(ARGUMENT_RESULT_LIST_TYPE) as ResultListType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView =  inflater.inflate(R.layout.fragment_scanner_history, container, false)
        return mView
    }


}