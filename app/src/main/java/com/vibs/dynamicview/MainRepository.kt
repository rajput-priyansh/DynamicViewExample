package com.vibs.dynamicview

import com.vibs.dynamicview.api.ApiRepository
import androidx.lifecycle.LiveData
import com.vibs.dynamicview.models.ResponseViews
import androidx.lifecycle.MutableLiveData
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainRepository() {

    private val apiRepository = ApiRepository().getGlobalInstance()

    fun getViewList(): LiveData<ResponseViews?> {
        val liveData = MutableLiveData<ResponseViews?>()
        apiRepository.getViewList()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ResponseViews?> {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                    liveData.value = null
                }

                override fun onNext(responseMovies: ResponseViews?) {
                    liveData.value = responseMovies
                }
            })
        return liveData
    }
}