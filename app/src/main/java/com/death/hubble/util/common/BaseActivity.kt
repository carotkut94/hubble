package com.death.hubble.util.common

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.get

abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable:CompositeDisposable = get()

    private lateinit var progressDialog: ProgressDialog
    @get:LayoutRes
    protected abstract val layoutRes: Int


    protected abstract fun setupView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        progressDialog = ProgressDialog(this)
        setupView(savedInstanceState)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }


    protected fun showProgressDialog(message:String, cancelable:Boolean){
        progressDialog.setMessage(message)
        progressDialog.setCancelable(cancelable)
        progressDialog.show()
    }

    protected fun hideProgressDialog(){
        if(progressDialog.isShowing){
            progressDialog.dismiss()
        }
    }

    protected fun showMessage(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}