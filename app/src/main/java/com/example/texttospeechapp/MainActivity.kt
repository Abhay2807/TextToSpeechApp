package com.example.texttospeechapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts:TextToSpeech?=null
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts= TextToSpeech(this, this)

        binding?.btn?.setOnClickListener{

            // If text input is null
            if(binding?.etText?.text!!.isEmpty()){
                Toast.makeText(this@MainActivity,
                "Please enter some text to speak",
                Toast.LENGTH_SHORT).show()
            }
            else // Speak out the text
            {
                speakText(binding?.etText?.text.toString())

            }
        }

    }

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){ // if TTS is ready

            val result=tts!!.setLanguage(Locale.US) // Selecting the language

            if(result == TextToSpeech.LANG_MISSING_DATA || //Possible errors
                    result== TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","Language Specified is not supported!!")
            }

        }

        else{ // if TTS is not ready
            Log.e("TTS","Initialization Failed!!")
        }

    }

    private fun speakText(Text:String){ // Speaking out the text

        tts?.speak(Text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts!=null)
        {
            tts?.stop()
            tts?.shutdown()
        }
        binding=null
    }


}