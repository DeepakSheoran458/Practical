package com.demo.practical.network

sealed class Responce<A>(var data: A? = null, val error: String? = null) {
    class  Loading<A> : Responce<A>()
    class Success<A>(data: A? = null) : Responce<A>(data = data)
    class Error<A>(errorMesseage: String) : Responce<A>(error = errorMesseage)
    class NetworkError<A> : Responce<A>()
}