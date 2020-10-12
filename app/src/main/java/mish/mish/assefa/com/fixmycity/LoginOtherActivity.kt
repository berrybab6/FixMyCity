package mish.mish.assefa.com.fixmycity

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login_other.*
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import retrofit2.Retrofit

class LoginOtherActivity : AppCompatActivity() {
lateinit var iMyService: IMyService
internal var compositeDisposable=CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_other)
  /*  //init
        val retrofit:Retrofit=RetrofitClient.getInstance()
        iMyService=retrofit.create(IMyService::class.java)
*/
        login_other_btn.setOnClickListener {
        val inte = Intent(this@LoginOtherActivity,RequestActivity::class.java)
        startActivity(inte)}

    }
}
