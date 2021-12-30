package com.hg.esp_tcp

import android.net.ConnectivityManager
import java.net.UnknownHostException
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.UiThread
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class TcpThread : Thread() {
    var socket = Socket()
    var temp: View? = null


    lateinit var writeSocket: DataOutputStream
    lateinit var readSocket: DataInputStream

    private val tcpId:String = "C1"

    val ip = "192.168.0.10"
    val port = 8092

    private val Spliter = '!'

    private var sendOrder:String = "/sendId"
    private var received:Boolean = false

    private var receiveOrder:String? = ""
    var receiveValue:String? = ""
    private var receiveRight:Boolean = false

    override fun run(){
        try {
            socket = Socket(ip,port)
            var out:StringBuffer = StringBuffer()
            writeSocket = DataOutputStream(socket.outputStream)
            readSocket = DataInputStream(socket.inputStream)
            var buffer = ByteArray(1024)
            while (!socket.isClosed){

//                 Log.d("TcpThread", "NowThread")

                // 송신
                var sendString :String
                when(sendOrder){
                    "/clear" -> sendString= "${tcpId}${Spliter}${sendOrder}${Spliter}0"
                    "/checkA" -> sendString="${tcpId}${Spliter}${sendOrder}${Spliter}0"
                    "/checkV" -> sendString="${tcpId}${Spliter}${sendOrder}${Spliter}0"
                    else -> sendString="${tcpId}${Spliter}/sendId${Spliter}0"
                }
                if(!received){
                    writeSocket.write(stringToByteArray(sendString))
                }
                sleep(1000)

                // 수신
                if(readSocket.available() != 0){
                    readSocket.read(buffer)
                    val receiveString = String(buffer).split('\n')

                    for (i: Int in 0 until receiveString.size-1){
                        Log.d("TcpThread", receiveString[i])
                        receiveOrder = receiveString[i].split('!')[1]
                        Log.d("TcpThread", receiveOrder.toString())
                        when(receiveOrder){
                            "/received" -> received = true
                            "/warn" -> {
                                receiveValue = receiveString[i].split('!')[2].toString()
                                MainActivity.handler.post(Runnable {
                                    MainActivity.textView!!.setText(receiveValue.toString())
                                })
                            }
                            "/sendT" -> receiveValue = receiveString[i].split('!')[2].toString()
                        }
                    }
                    receiveRight = true
                    buffer = ByteArray(1024)
                }
            }

            Log.d("TcpThread", "END")
        }
        catch (uhe: UnknownHostException){
            Log.d("TcpThread", "UnknownHostException")

        }
    }

    override fun interrupt() {
        Log.d("TcpThread", "interrupt")
        super.interrupt()
    }

    fun stringToByteArray(str:String): ByteArray {
        val byteArray = str.toByteArray()
        return byteArray
    }

    public fun sendMessage(str:String){
        sendOrder = str
        received = false
        receiveRight = false
    }

    public fun getTemp(): String? {
        while(!receiveRight){
            sleep(10)
        }
        return receiveValue
    }
}