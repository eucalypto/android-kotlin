package de.eucalypto.eucalyptapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Handy extension function to get immutable LiveData from MutableLiveData object
 */
fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this