package mish.mish.assefa.com.fixmycity.Retrofit

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Headers


internal abstract class Response<T> {
    val isSuccess: Boolean=false // true if request status code is OK otherwise false
    abstract fun code(): Int  //returns HTTP code. e.g if success 200
    abstract fun message(): String  // message describing the code e.g if success OK
    abstract fun headers(): Headers  // HTTP headers
    abstract fun body(): T  // main body of response. It can be casted to desired model.
    // in our example it is casted to User type.
    abstract fun errorBody(): ResponseBody  // separate information about error

    abstract fun raw(): Response  // Raw okhttp message
}