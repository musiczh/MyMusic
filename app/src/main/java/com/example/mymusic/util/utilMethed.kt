package com.example.mymusic.util

import java.util.*
import java.util.concurrent.*

fun getTimeString(duration:Int):String{
    val second =
        if (duration%1000!=0) duration/1000+1
        else duration/1000
    val min = addZero(second/60)
    val s = addZero(second%60)
    return "$min:$s"
}
private fun addZero(num:Int):String{
    val string = "$num"
    return if (string.length==1) "0$string"
    else string
}

private val schedulePool = Executors.newScheduledThreadPool(2)
fun setTimeTask(delaySeconds:Long,task:()->Unit):ScheduledFuture<*>{
    val timeTask = object : TimerTask(){
        override fun run() = task()
    }
    return schedulePool.scheduleWithFixedDelay(timeTask,0,delaySeconds,TimeUnit.SECONDS)
}

