package me.newbly.camyomi.presentation.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import me.newbly.camyomi.databinding.EditAlertDialogBinding

class EditDialogFragment : DialogFragment() {
    private var onDialogPositiveButtonClickListener: ((String) -> Unit)? = null

    private var _binding: EditAlertDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = EditAlertDialogBinding.inflate(inflater)

            builder.setView(binding.root)
                .setPositiveButton("Edit",
                    DialogInterface.OnClickListener { dialog, id ->
                        onDialogPositiveButtonClickListener?.invoke(binding.editText.text.toString())
                        dialog.dismiss()
                })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setOnDialogPositiveButtonClickListener(l: (String) -> Unit) {
        onDialogPositiveButtonClickListener = l
    }
}