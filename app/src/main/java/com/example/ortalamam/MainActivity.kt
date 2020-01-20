package com.example.ortalamam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*

class MainActivity : AppCompatActivity() {

    private val DERSLER = arrayOf("Matematik","Algoritma","Devre Teorileri","Lojik Tasarım","C Programlama")
    private var tumDerslerArray:ArrayList<Dersler> = ArrayList(5)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (rootlayout.childCount ==0){
            btnHesapla.visibility = View.INVISIBLE
        }
        else btnHesapla.visibility = View.VISIBLE



        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,DERSLER)
        etDers.setAdapter(adapter)

        btnEkle.setOnClickListener {
        if (!etDers.text.isNullOrEmpty()){



                var inflater = layoutInflater

                var yeniDersview= inflater.inflate(R.layout.yeni_ders_layout,null)

                var dersAd = etDers.text.toString()
                var dersKredi = spnKredi.selectedItem.toString()
                var dersNot= spnDersNot.selectedItem.toString()

                yeniDersview.etYeniDers.setText(dersAd)
                yeniDersview.spnYeniKredi.setSelection(spinnerIndex(spnKredi,dersKredi))
                yeniDersview.spnYeniDersNot.setSelection(spinnerIndex(spnDersNot,dersNot))

                yeniDersview.etYeniDers.setAdapter(adapter)

                yeniDersview.btnSil.setOnClickListener {

                    rootlayout.removeView(yeniDersview)
                    if (rootlayout.childCount ==0){
                        btnHesapla.visibility = View.INVISIBLE
                    }else btnHesapla.visibility = View.VISIBLE
                }

                rootlayout.addView(yeniDersview)

                     sifirla()

                btnHesapla.visibility = View.VISIBLE


            }
        else {

            //Toast.makeText(this,"Ders Adı Giriniz",Toast.LENGTH_LONG).show()
            FancyToast.makeText(this,"Ders Adı Giriniz !",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
            }

        }


    }
    fun sifirla(){
        etDers.setText("")
        spnKredi.setSelection(0)
        spnDersNot.setSelection(0)

    }

    fun ortalamaHeaspla(view: View){

        var toplamKredi = 0.0
        var toplamNot= 0.0

        for(i in 0..rootlayout.childCount-1){

            var teksatir = rootlayout.getChildAt(i)

            var geciciDers = Dersler(teksatir.etYeniDers.text.toString(),teksatir.spnYeniDersNot.selectedItem.toString(),
                ((teksatir.spnYeniKredi.selectedItemPosition)+1).toString())

            tumDerslerArray.add(geciciDers)
        }
        for (oankiders in tumDerslerArray ){

            toplamNot += harfiNotaCevir(oankiders.dersNot)*(oankiders.dersKredi.toDouble())
            toplamKredi += oankiders.dersKredi.toDouble()

        }
       // Toast.makeText(this,"Ortalama : "+(toplamNot/toplamKredi),Toast.LENGTH_LONG).show()
        FancyToast.makeText(this,"Ortalama : "+(toplamNot/toplamKredi),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()
        tumDerslerArray.clear()

    }

    fun harfiNotaCevir(str:String):Double{

        var deger = 0.0
        when(str){
            "AA"-> deger = 4.0
            "BA"-> deger = 3.5
            "BB"-> deger = 3.0
            "CB"-> deger = 2.5
            "CC"-> deger = 2.0
            "DC"-> deger = 1.5
            "DD"-> deger = 1.0
            "FF"-> deger = 0.0
        }
        return deger

    }

    fun spinnerIndex(spinner: Spinner,aranacak:String):Int{
        var index = 0
        for(i in 0..spinner.count){

            if(spinner.getItemAtPosition(i).toString().equals(aranacak)){

                index=i
                break
            }
        }
        return index

    }
}
