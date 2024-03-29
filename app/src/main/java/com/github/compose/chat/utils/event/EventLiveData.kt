package com.github.compose.chat.utils.event


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * A custom LiveData which can unregister when there is no observer
 */
class EventLiveData( private val mSubject: String) : LiveData<ConsumableEvent>() {

    fun update(obj: ConsumableEvent) {
        postValue(obj)
    }

    override fun removeObserver(observer: Observer<in ConsumableEvent>) {
        super.removeObserver(observer)
        if (!hasObservers()) {
            LiveDataBus.unregister(mSubject)
        }
    }
}