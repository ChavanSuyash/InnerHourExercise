package com.innerhour.exercise.features.todotaskdetails.ui.commentdialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.innerhour.exercise.R
import com.innerhour.exercise.features.todolist.data.entities.Comment

/**
 * Created by Suyash Chavan.
 */
class CommentDialog : DialogFragment() {

    private var mCommentListener: CommentListener? = null
    internal interface CommentListener {
        fun onComment(comment: Comment)
    }

    lateinit var commentDialogView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        commentDialogView = inflater.inflate(R.layout.dialog_comment, container, false)
        val add = commentDialogView.findViewById<Button>(R.id.add_comment)
        add.setOnClickListener {
            if (mCommentListener != null) {
                val commentText = commentDialogView.findViewById<EditText>(R.id.comment_text).text.toString()
                mCommentListener!!.onComment(Comment(commentText))
            }

            dismiss()
        }
        val cancel = commentDialogView.findViewById<Button>(R.id.cancel_dialog)
        cancel.setOnClickListener {
            dismiss()
        }
        return commentDialogView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCommentListener = targetFragment as CommentListener
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}