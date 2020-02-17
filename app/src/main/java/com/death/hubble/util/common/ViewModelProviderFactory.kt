package com.death.hubble.util.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

/**
 * A generic ViewModelProvider Factory to allows us to create the ViewModel with args
 */

class ViewModelProviderFactory<VM: ViewModel>(
    private val  kClass: KClass<VM>,
    private val creator: () -> VM
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(kClass.java)) return creator() as T
        throw IllegalArgumentException("Unknown class name")
    }

}