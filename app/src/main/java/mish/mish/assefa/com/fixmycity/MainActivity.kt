package mish.mish.assefa.com.fixmycity


import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler: Handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val intent= Intent("mish.mish.assefa.com.fixmycity.LoginActivity")
                //=new Intent(SplashActivity.this,MainActivity.class)
                startActivity(intent)
                finish()
            }
        },2900)

    }
}