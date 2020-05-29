package com.hxz.baseui.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @title com.hxz.baseui.base  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SingleLiveEvent
 * @DATE 2020/5/29  10:27 星期五
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(true)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true,false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever {
            if (mPending.compareAndSet(true,false)) {
                observer.onChanged(it)
            }
        }
    }

    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }
}