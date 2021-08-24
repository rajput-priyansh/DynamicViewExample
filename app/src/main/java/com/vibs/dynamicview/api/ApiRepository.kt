package com.vibs.dynamicview.api


class ApiRepository {


    fun getGlobalInstance(): ApiInterface {
        return ApiClient().retroClient
    }
}