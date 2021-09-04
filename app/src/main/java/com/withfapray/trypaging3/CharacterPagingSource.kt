package com.withfapray.trypaging3

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.withfapray.trypaging3.network.CharacterData
import com.withfapray.trypaging3.network.RetroService

class CharacterPagingSource(val apiService: RetroService) : PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_NUMBER
            val response = apiService.getDataFromApi(nextPage)
            Log.d("CEK_DATA","${response.info}")
            var nextPageNumber : Int? = null

            if (response.info.next != null ){
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                if (nextPageQuery != null) {
                    nextPageNumber = nextPageQuery.toInt()

                    Log.d(MainActivity.TAG, "Page : $nextPageNumber")
                }
            }
            LoadResult.Page(response.results,null,nextPageNumber)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
    }
}