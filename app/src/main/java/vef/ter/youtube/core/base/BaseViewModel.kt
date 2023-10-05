package vef.ter.youtube.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    protected val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun <T> doOperation(
        operation: suspend ()->Result<T>,
        success: (T) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val result = operation()
            when{
                result.isSuccess -> {
                    result.onSuccess(success)
                }
                    result.isFailure-> {
                        result.onFailure{
                            exception: Throwable ->
                            if (!exception.message.isNullOrEmpty()){
                                _error.postValue(exception.message)
                            }


                        }
                _loading.postValue(false)

            }            }

        }
    }
}