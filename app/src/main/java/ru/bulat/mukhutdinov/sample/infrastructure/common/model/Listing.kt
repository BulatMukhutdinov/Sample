package ru.bulat.mukhutdinov.sample.infrastructure.common.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.infrastructure.util.Either

data class Listing<T>(
    val pagedList: LiveData<Either<PagedList<T>, SampleException>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)