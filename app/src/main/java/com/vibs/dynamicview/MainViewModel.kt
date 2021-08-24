package com.vibs.dynamicview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vibs.dynamicview.models.ResponseViews

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _viewResponse = MutableLiveData<ResponseViews>()
    var viewResponse: LiveData<ResponseViews?> = _viewResponse

    fun setResponseView(data: ResponseViews) {
        _viewResponse.value = data
    }

    /**
     * Make API call to get View list
     * @return
     */
    fun getMovies(): LiveData<ResponseViews?> {
        return repository.getViewList()
    }
}