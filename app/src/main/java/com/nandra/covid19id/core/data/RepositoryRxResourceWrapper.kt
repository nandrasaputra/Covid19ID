package com.nandra.covid19id.core.data

import android.util.Log
import com.nandra.covid19id.core.data.source.remote.network.ApiResponse
import com.nandra.covid19id.utils.Constant
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class RepositoryRxResourceWrapper<ResultType, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        val observable = Observable.just(Resource.Loading<ResultType>())
        val outerDisposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe{
                observable.unsubscribeOn(Schedulers.io())
                fetchFromNetwork()
            }
        mCompositeDisposable.add(outerDisposable)
    }

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun dataMapper(input: RequestType) : ResultType

    protected abstract fun emptyDataMapper() : ResultType

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.onNext(Resource.Loading())
        val innerDisposable = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe { response ->
                when(response) {
                    is ApiResponse.Success -> {
                        result.onNext(Resource.Success(dataMapper(response.data)))
                    }
                    is ApiResponse.Empty -> {
                        try {
                            val emptyResult = emptyDataMapper()
                            result.onNext(Resource.Success(emptyResult))
                        } catch (exception: Exception) {
                            result.onNext(Resource.Error("Something is wrong :(", null))
                            Log.e(Constant.TAG, exception.message.toString() )
                        }
                    }
                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(response.errorMessage, null))
                    }
                }
            }
        mCompositeDisposable.add(innerDisposable)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)

}