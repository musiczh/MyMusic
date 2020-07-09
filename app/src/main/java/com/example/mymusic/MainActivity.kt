package com.example.mymusic

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.mymusic.service.MusicService
import com.example.mymusic.view.activity.MediaService


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mContext: Context
    }

    val mediaService = MediaService()
    private lateinit var navController : NavController
    private  var toast: Toast? = null

    private val connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {}
        override fun onServiceConnected(name: ComponentName?, service: IBinder?){
            if(service == null){
                Log.e("huan_MainActvity","服务返回的IBinder==null")
                showToast("程序出现异常请重启")
                this@MainActivity.finish()
            }else{
                val mBinder = service as MusicService.MyBinder
                mediaService.setBinder(mBinder)
                mediaService.putObserve()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = applicationContext
        requestPermissions()
        val intentService = Intent(this,MusicService::class.java)
        bindService(intentService,connection,Context.BIND_AUTO_CREATE)
        navController = findNavController(this,R.id.main_fragment_container)
    }

    fun navigate(id:Int) = navController.navigate(id)
    fun navBackStack() = navController.popBackStack()

    fun showToast(string:String){
        if (toast==null) toast = Toast.makeText(this,string,Toast.LENGTH_SHORT)
        else{
            toast?.apply {
                setText(string)
                duration = Toast.LENGTH_SHORT
            }
        }
        toast?.show()
    }



    private fun requestPermissions(){
        val session = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (session!= PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1-> if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                showToast("请赋予我们权限")
                finish()
                }

        }
    }
}
