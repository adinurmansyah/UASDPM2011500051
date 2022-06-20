package com.example.uasdpm2011500051

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterCampuss(
    private val getContext: Context,
    private val customListItem:ArrayList<PenampungRecordCampuss>
): ArrayAdapter <PenampungRecordCampuss>(getContext,0,customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflatelist = (getContext as Activity).layoutInflater
            listLayout = inflatelist.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNidn = listLayout.findViewById(R.id.tvNidn)
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvJabatan = listLayout.findViewById(R.id.tvJabatan)
                tvGolonganPangkat = listLayout.findViewById(R.id.tvGolonganPangkat)
                tvPendidikan = listLayout.findViewById(R.id.tvPendidikan)
                tvBidangKeahlian = listLayout.findViewById(R.id.tvBidangKeahlian)
                tvProgramStudi = listLayout.findViewById(R.id.tvProgramStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNidn!!.setText(listItem.nidn)
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvJabatan!!.setText(listItem.jabatan)
        holder.tvGolonganPangkat!!.setText(listItem.golonganpangkat)
        holder.tvPendidikan!!.setText(listItem.pendidikan)
        holder.tvBidangKeahlian!!.setText(listItem.keahlian)
        holder.tvProgramStudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, PengentriDataActivity::class.java)
            i.putExtra("kode", listItem.nidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("Jabatan", listItem.jabatan)
            i.putExtra("golongan_pangkat", listItem.golonganpangkat)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_Studi", listItem.programstudi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener {
            val db = campuss(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvNidn!!.text
            val nama = holder.tvNmDosen!!.text
            val jabatan = holder.tvJabatan!!.text
            val golonganpangkat = holder.tvGolonganPangkat!!.text
            val pendidikan = holder.tvPendidikan!!.text
            val Keahlian = holder.tvBidangKeahlian!!.text
            val Program_Studi = holder.tvProgramStudi!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage(""" Apakah Anda yakin akan menghapus data ini?
                    $nama [$kode-$jabatan-$golonganpangkat-$pendidikan-$Keahlian-$Program_Studi]
                """.trimIndent())
                setPositiveButton("Ya") {_, _->
                        if (db.hapus("$kode"))
                            Toast.makeText(
                                context,
                                "Data Dosen berhasil dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                    setNegativeButton("Tidak", null)
                    create().show()
                }
            }
            return listLayout!!
        }
        class ViewHolder {
            internal var tvNidn: TextView? = null
            internal var tvNmDosen: TextView? = null
            internal var tvJabatan: TextView? = null
            internal var tvGolonganPangkat: TextView? = null
            internal var tvPendidikan: TextView? = null
            internal var tvBidangKeahlian: TextView? = null
            internal var tvProgramStudi: TextView? = null
            internal var btnEdit: ImageButton? = null
            internal var btnHapus: ImageButton? = null
        }
    }