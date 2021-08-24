package com.vibs.dynamicview.api

import com.vibs.dynamicview.models.ResponseViews
import retrofit2.http.GET
import rx.Observable

interface ApiInterface {
    @GET("taskhuman/test")
    fun getViewList(): Observable<ResponseViews?>?
}