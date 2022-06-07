package com.harunkor.kotlincoroutinessample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val TAG = "DGPAYS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



      sample2()







    }

    private fun sample6(){

        runBlocking {
            launch(Dispatchers.Default) {
                println("Context: $coroutineContext" )
                withContext(Dispatchers.IO){
                    println("Context: $coroutineContext")
                }
            }
        }
    }

    private fun sample5(){

        var name = ""
        var age = 0

        runBlocking  {
            val job = launch {
                val downloadName = async { downloadName() }
                val downloadAge = async { downloadAge() }
                name = downloadName.await()
                age = downloadAge.await()
            }


            job.invokeOnCompletion {
                Log.v(TAG,"$name - $age")
            }


        }
    }

    suspend fun downloadAge() : Int {
       delay(10000L)
       Log.v(TAG,"Yas degeri servisten geldi.")
       return 36
    }

    suspend fun downloadName(): String {
        delay(5000L)
        Log.v(TAG,"İsim servisten geldi")
        return "HARUN"
    }

    private fun sample4(){
        GlobalScope.launch {
            launch(Dispatchers.Main) {
                Log.v(TAG,"Main ")
            }
            launch(Dispatchers.Default){
                Log.v(TAG,"Default ")
            }
            launch(Dispatchers.Unconfined) {
                Log.v(TAG,"Unconfined ")
            }
            launch(Dispatchers.IO) {
                Log.v(TAG,"IO")
            }

        }
    }

    private fun sample3(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.v(TAG,"Main ")
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.v(TAG,"IO")
        }

        CoroutineScope(Dispatchers.Default).launch {
            Log.v(TAG,"Default")
        }

    }


    private fun sample2(){
         CoroutineScope(Dispatchers.IO).launch {
         val answer = doNetworkCall()
         withContext(Dispatchers.Main) {
             Log.v(TAG,answer)
         }
     }
    }

    private fun sample1(){
          runBlocking {
          val job = launch {
              repeat(10) { i ->
                  Log.v(TAG, "repeat : $i.toString() ")
                  delay(500L)
              }
          }

          delay(1500L)

          job.invokeOnCompletion {
              Log.v(TAG,"BİTTİ")
          }

          job.cancel()
          Log.v(TAG,"iptal edildi.")


      }
    }


    suspend fun doNetworkCall(): String {
        delay(10000L)
        return "Network call answer."

    }
}