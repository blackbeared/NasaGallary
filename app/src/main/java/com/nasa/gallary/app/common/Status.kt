package com.nasa.gallary.app.common

sealed class Status {

    object Content : Status()

    data class StatusError(val exception: Throwable) : Status()

    object Loading : Status()

    fun errorText(): String {
        return if (this is StatusError){
            exception.localizedMessage?:""
        } else {
            ""
        }
    }
}