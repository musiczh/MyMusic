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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.mymusic.model.entity.Music
import com.example.mymusic.service.MusicService


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mContext: Context
    }
    private val mapObservers = HashMap<String,(music:Music)->Unit>()

    private lateinit var navController : NavController
    private  var mBinder : MusicService.MyBinder? = null
    private  var toast: Toast? = null

    private val connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {}
        override fun onServiceConnected(name: ComponentName?, service: IBinder?){
            mBinder = service as MusicService.MyBinder
            for (mapObserver in mapObservers) {
                mBinder?.subscribe(mapObserver.key,mapObserver.value)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = applicationContext

        val intentService = Intent(this,MusicService::class.java)
        bindService(intentService,connection,Context.BIND_AUTO_CREATE)

        navController = findNavController(R.id.main_fragment_container)
    }

    fun navigate(id:Int) = navController.navigate(id)
    fun navBackStack() = navController.popBackStack()
    fun playMusic(list:ArrayList<Music>,position:Int){
        Log.d("MainActivity","播放音乐$position")
        mBinder?.playMusicIndex(list,position) }
    fun playNextMusic() = mBinder?.nextMusic()
    fun playPreviousMusic() = mBinder?.previousMusic()
    fun pauseMusic() = mBinder?.pausePlay()
    fun continueMusic() = mBinder?.continuePlay()
    fun getCurrentTime():Int = mBinder?.currentTime() ?: -1
    fun subscribe(name: String,observer:(music:Music)->Unit) = mBinder?.subscribe(name,observer)
    fun subscribeOnCreate(name: String,observer:(music:Music)->Unit) = mapObservers.put(name,observer)
    fun unSubscribe(name:String) = mBinder?.unSubscribe(name)
    fun getCurrentMusic() = mBinder?.getCurrentMusic()
    fun isPlaying() = mBinder?.isPlaying()
    fun seekTo(progress:Int)=mBinder?.seekToProcess(progress)

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
