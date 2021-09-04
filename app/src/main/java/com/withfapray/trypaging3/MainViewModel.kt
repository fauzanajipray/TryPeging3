package com.withfapray.trypaging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.withfapray.trypaging3.network.CharacterData
import com.withfapray.trypaging3.network.RetroService
import com.withfapray.trypaging3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private var retroService: RetroService = RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)

    fun getListData(): Flow<PagingData<CharacterData>> {
        return Pager (config = PagingConfig(pageSize = 34,maxSize = 200),
            pagingSourceFactory = {CharacterPagingSource(retroService)}).flow.cachedIn(viewModelScope)
    }

}