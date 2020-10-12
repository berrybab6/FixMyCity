package mish.mish.assefa.com.fixmycity.data.user
import java.io.Serializable

data class User (
    val first_name:String,
    val last_name:String,
    val password:String,
    val email: String,
    val _id:String,
    val username:String,
    val token:String
    // val token:String=""
): Serializable
