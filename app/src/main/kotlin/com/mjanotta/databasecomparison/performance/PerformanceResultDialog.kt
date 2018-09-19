package com.mjanotta.databasecomparison.performance

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.mjanotta.databasecomparison.R
import com.mjanotta.databasecomparison.performance.entity.PerformanceResult
import kotlinx.android.synthetic.main.dialog_performance_result.view.*
import org.parceler.Parcels

class PerformanceResultDialog : DialogFragment() {

    companion object {
        const val EXTRA_RESULT = "extraResult"
        const val TAG = "PerformanceResultDialog"

        fun create(performanceResult: PerformanceResult): PerformanceResultDialog {
            val dialog = PerformanceResultDialog()
            val args = Bundle()
            args.putParcelable(EXTRA_RESULT, Parcels.wrap(performanceResult))
            dialog.arguments = args
            return dialog
        }
    }

    lateinit var performanceResult: PerformanceResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performanceResult = Parcels.unwrap(arguments.getParcelable(EXTRA_RESULT))
    }

    @SuppressWarnings("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_performance_result, null)
        val alertDialog = AlertDialog.Builder(activity)
                .setTitle(R.string.performanceResult)
                .setPositiveButton(R.string.performanceOk) { dialog, _ -> dialog.dismiss() }
                .setView(view)
                .create()
        onViewCreated(view, savedInstanceState)
        return alertDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.createTime.text = getString(R.string.performanceSave, performanceResult.saveTime)
        view.readTime.text = getString(R.string.performanceRead, performanceResult.readTime)
        view.queryNearTime.text = getString(R.string.performanceQueryNear, performanceResult.queryNearTime)
        view.queryFarTime.text = getString(R.string.performanceQueryFar, performanceResult.queryFarTime)
        view.deleteTime.text = getString(R.string.performanceDelete, performanceResult.deleteTime)
    }
}