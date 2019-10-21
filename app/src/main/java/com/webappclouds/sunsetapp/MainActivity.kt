package com.webappclouds.sunsetapp

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getSunset(view: View) {
        var city = etCityName.text.toString()
        val url = "https://api.sunrise-sunset.org/json?lat=39.702770&lng=-75.111961"
        MyAsyncTask().execute(url)
        Log.wtf("TAG", "Called")
    }

    inner class MyAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg p0: String?): String {
            try {
                val url = URL(p0[0])

                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 7000

                var inString = convertStreamToString(urlConnect.inputStream)

                publishProgress(inString)
            } catch (ex: Exception) {

            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json = JSONObject(values[0])
                val results = json.getJSONObject("results")
                val sunrise = results.getString("sunrise")
                Log.wtf("TAG", "Query: $results")

                tvSunSetTime.text = " Sunrise is $sunrise"
            } catch (ex: Exception) {
            }
        }

        override fun onPostExecute(result: String?) {

        }
    }

    fun convertStreamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var allString = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    allString += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return allString
    }
}
