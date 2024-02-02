package com.ecommerce.task.util.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ecommerce.task.data.RepoOperations
import com.ecommerce.task.data.bodyReq.GetRelatedProductsReq
import com.ecommerce.task.data.model.Product

class ProductPagingSource(
    private val brandId: Int,
    private val repoOperations: RepoOperations
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val page = params.key ?: 1
            val response = repoOperations.getRelatedProducts(GetRelatedProductsReq(brandId, page))
            val products =
                response.body()?.results // Assuming your API response structure has a "data" field

            // Compute next key (page number)
            val nextPage = page + 1

            return LoadResult.Page(
                data = products ?: listOf(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if ((products ?: listOf()).isNotEmpty()) nextPage else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        // We need to refresh data if the user pulls to refresh
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}