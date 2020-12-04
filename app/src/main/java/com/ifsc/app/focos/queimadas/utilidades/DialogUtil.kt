package com.ifsc.app.focos.queimadas.utilidades


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.ifsc.app.focos.queimadas.R
import kotlinx.android.synthetic.main.layout_dialog_ok.view.*

object DialogUtil {
    fun showDialogGenericMessageOk (context: Context, img : Int?, titulo : String, msm : String, onYesClickListener : View.OnClickListener?) : AlertDialog {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.layout_dialog_ok, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.create()
        mAlertDialog.setCancelable(false)

        if(titulo.isNotEmpty()) {
            mDialogView.text_title.text = titulo
        } else {
            mDialogView.text_title.visibility = View.GONE
        }

        mDialogView.text_title.text = titulo
        mDialogView.text_msm.text = msm
        if (img != null) {
            mDialogView.img.setImageResource(img)
        } else {
            mDialogView.img.visibility = View.GONE
        }
        mAlertDialog.window!!.attributes.windowAnimations = R.style.StyledDialogs_DialogAnimationNormal

        mDialogView.button_yes.setOnClickListener {
            onYesClickListener?.onClick(it)
            mAlertDialog.dismiss()
        }

        return mAlertDialog
    }
}
