package com.hg.esp_tcp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstFragmentViewModel : ViewModel() {

    private var _text = MutableLiveData<String>().apply {
//        runOnUiThread {
//            while (true){
//                if(!socket.receiveValue.isNullOrEmpty()){
//                    if(socket.receiveValue!!.toInt() > 30){
//                        textView.text = "asdf"
//                    }
//                }
//            }
//        }
    }
    var text: LiveData<String> = _text
}