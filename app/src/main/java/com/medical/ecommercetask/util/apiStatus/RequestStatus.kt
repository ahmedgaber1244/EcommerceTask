package com.medical.ecommercetask.util.apiStatus

sealed class RequestStatus<T>{
    class Loading<T> : RequestStatus<T>()
    class Failure<T>(val data:T) : RequestStatus<T>()
    class Success<T>(val data:T) : RequestStatus<T>()
    class NetworkLost<T> : RequestStatus<T>()
    class AuthenticationError<T>(val data:T) : RequestStatus<T>()
    class Idle<T> : RequestStatus<T>()
}