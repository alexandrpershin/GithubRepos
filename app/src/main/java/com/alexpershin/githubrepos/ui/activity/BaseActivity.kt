package com.alexpershin.githubrepos.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.alexpershin.githubrepos.R
import com.alexpershin.githubrepos.utils.makeGone
import com.alexpershin.githubrepos.utils.makeVisible
import com.google.android.material.snackbar.Snackbar

/**
 * This class is base for [AppCompatActivity] which contains basic necessary methods and fields
 * */

abstract class BaseActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var container: View? = null

    /**
     * Abstract method which has to be overridden in order to provide activity layout id
    * */

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        progressBar = findViewById(R.id.progressBar)
        container = findViewById(R.id.container)
    }

    fun showSnackBar(message: String) {
        val view = this.findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    fun hideProgressBar() {
        container?.alpha = 1.0f
        progressBar?.makeGone()
    }

    fun showProgressBar() {
        container?.alpha = 0.5f
        progressBar?.makeVisible()
    }

}
