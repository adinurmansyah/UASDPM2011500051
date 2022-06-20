package com.example.uasdpm2011500051

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class PengentriDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengentri_data)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("golongan_pangkat")
                && intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian")
                && intent.hasExtra("Program_Studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolonganPangkat = findViewById<Spinner>(R.id.spnGolonganPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etBidangKeahlian = findViewById<EditText>(R.id.etBidangKeahlian)
        val etProgramStudi = findViewById<EditText>(R.id.etProgramStudi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val Jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor", "Lektor Kepala", "Guru Besar")
        val adpJabatan = ArrayAdapter(
            this@PengentriDataActivity,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        val GolonganPangkat = arrayOf("III/a - Penata Muda", "III/b - Penata Muda Tingkat I", "III/c - Penata", "III/d - Penata Tingkat I", "IV/a - Pembina", "IV/b - Pembina Tingkat I", "IV/c - Pembina Utama Muda", "IV/d - Pembina Utama Madya", "IV/e - Pembina Utama")
        val adpGolonganPangkat = ArrayAdapter(
            this@PengentriDataActivity,
            android.R.layout.simple_spinner_dropdown_item,
            GolonganPangkat
        )
        spnJabatan.adapter = adpJabatan
        spnGolonganPangkat.adapter = adpGolonganPangkat

        if (modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("Jabatan")
            val golonganpangkat = intent.getStringExtra("golongan_pangkat")
            val Pendidikan = intent.getStringExtra("Pendidikan")
            val Keahlian = intent.getStringExtra("Keahlian")
            val programstudi = intent.getStringExtra("Program_Studi")

            etNIDN.setText(kode)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(Jabatan.indexOf(jabatan))
            spnGolonganPangkat.setSelection(GolonganPangkat.indexOf(golonganpangkat))
            if (Pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidangKeahlian.setText(Keahlian)
            etProgramStudi.setText(programstudi)
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked) && "${etBidangKeahlian.text}".isNotEmpty() &&
                "${etProgramStudi.text}".isNotEmpty()
            ) {
                val db = campuss(this@PengentriDataActivity)
                db.nidn = "${etNIDN.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golonganpangkat = spnGolonganPangkat.selectedItem as String
                db.pendidikan = if (rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etBidangKeahlian.text}"
                db.programstudi = "${etProgramStudi.text}"
                when {
                    !if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}") -> {
                        Toast.makeText(
                            this@PengentriDataActivity,
                            "Data Dosen gagal disimpan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@PengentriDataActivity,
                            "Data Dosen berhasil disimpan",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            } else
                Toast.makeText(
                    this@PengentriDataActivity,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}