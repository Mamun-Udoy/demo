package com.example.navifationview.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class PagingViewModel :ViewModel() {

//    val list = pager.getPager().cachedIn(viewModelScope)

    fun getData() = androidx.paging.Pager(
        config = PagingConfig(pageSize = 1, maxSize = 5),
        pagingSourceFactory = { PagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}