package com.hg.esp_tcp

import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.hg.esp_tcp.databinding.ActivityMainBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

//


    companion object {
        public var socket:TcpThread = TcpThread()
        public var handler = Handler(Looper.getMainLooper())
        public var textView: TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // test start
//        val contentsPreference = getSharedPreferences("contents", MODE_PRIVATE)
//        textView.setText(contentsPreference.getString("contents",""))
//
//        val runnable = Runnable {
//            contentsPreference.edit(true){
//                putString("contents", textView.getSt)
//            }
//        }
        // test end



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textView = binding.contentIdMain.textviewWarn

        setSupportActionBar(binding.toolbar)

        socket.start()


//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        /*asdfasfd*/

        binding.contentIdMain.buttonAlarm.setOnClickListener {
            MainActivity.socket.sendMessage("/checkA")
        }
        binding.contentIdMain.buttonValue.setOnClickListener{
            MainActivity.socket.sendMessage("/checkV")
            val temp = MainActivity.socket.getTemp()
            binding.contentIdMain.textviewSecond.setText(temp.toString())
        }
        binding.contentIdMain.buttonClear.setOnClickListener{
            MainActivity.socket.sendMessage("/clear")
            MainActivity.textView!!.setText("")
        }

        val textView: TextView = binding.contentIdMain.textviewWarn
//        textView.addTextChangedListener{
//
//        }


        /*asdfasfd*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}