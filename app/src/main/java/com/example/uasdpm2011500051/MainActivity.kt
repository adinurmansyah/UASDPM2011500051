package com.example.uasdpm2011500051

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpCampuss: AdapterCampuss
    private lateinit var dataCampuss: ArrayList<PenampungRecordCampuss>
    private lateinit var lvCampuss: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvCampuss = findViewById(R.id.lvCampuss)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataCampuss = ArrayList()
        adpCampuss = AdapterCampuss(this@MainActivity, dataCampuss)

        lvCampuss.adapter = adpCampuss

        refresh()
        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, PengentriDataActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean){
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh() {
        val db = campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dataCampuss.size) { dataCampuss.removeFirst() }
        if (data.count > 0) {
            while (data.moveToNext()) {
                val campuss = PenampungRecordCampuss(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpCampuss.add(campuss)
                adpCampuss.notifyDataSetChanged()
            }
            lvCampuss.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvCampuss.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}