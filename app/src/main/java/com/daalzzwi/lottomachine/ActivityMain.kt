package com.daalzzwi.lottomachine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

class ActivityMain : AppCompatActivity() {

    private val btnInitialization : Button by lazy {

        findViewById< Button >( R.id.btnInitialization )
    }

    private val btnAddNumber : Button by lazy {

        findViewById< Button >( R.id.btnAddNumber )
    }

    private val btnRun : Button by lazy {

        findViewById< Button >( R.id.btnRun )
    }

    private val npNumberPicker : NumberPicker by lazy {

        findViewById< NumberPicker >( R.id.npNumberPicker )
    }

    private val listNumber : List< TextView > by lazy {

        listOf< TextView >(
                findViewById< TextView >( R.id.tvNumber1 ),
                findViewById< TextView >( R.id.tvNumber2 ),
                findViewById< TextView >( R.id.tvNumber3 ),
                findViewById< TextView >( R.id.tvNumber4 ),
                findViewById< TextView >( R.id.tvNumber5 ),
                findViewById< TextView >( R.id.tvNumber6 )
        )
    }

    private var boolDidRun = false

    private val setPickNumber = hashSetOf< Int >()

    override fun onCreate( savedInstanceState: Bundle? ) {

        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        window.insetsController?.setSystemBarsAppearance( WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS , WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS )

        npNumberPicker.minValue = 1
        npNumberPicker.maxValue = 45

        initBtnRun()
        initBtnAddNumber()
        initBtnInitialization()
    }

    private fun initBtnInitialization() {

        btnInitialization.setOnClickListener {

            setPickNumber.clear()

            listNumber.forEach{
                it.isVisible = false
            }

            boolDidRun = false
        }
    }

    private fun initBtnAddNumber() {

        btnAddNumber.setOnClickListener {

            if( boolDidRun ) {

                Toast.makeText( this , "초기화 후에 시도해주세요" , Toast.LENGTH_SHORT ).show()
                return@setOnClickListener
            }

            if( setPickNumber.size >= 6 ) {

                Toast.makeText( this , "번호는 6 개까지만 선택할 수 있어요" , Toast.LENGTH_SHORT ).show()
                return@setOnClickListener
            }

            if( setPickNumber.contains( npNumberPicker.value ) ) {

                Toast.makeText( this , "이미 선택한 번호에요" , Toast.LENGTH_SHORT ).show()
                return@setOnClickListener
            }

            val textView = listNumber[setPickNumber.size]
            textView.isVisible = true
            textView.text = npNumberPicker.value.toString()

            setNumberBackground( npNumberPicker.value , textView )

            setPickNumber.add( npNumberPicker.value )
        }
    }

    private fun initBtnRun() {

        btnRun.setOnClickListener {

            val list = getRandomNumber()

            boolDidRun = true

            list.forEachIndexed { index , number ->

                val textView = listNumber[ index ]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground( number , textView )
            }
        }
    }

    private fun getRandomNumber(): List< Int > {

        val numberList = mutableListOf< Int >()
                .apply {

                    for( i in 1..45 ) {

                        if( setPickNumber.contains( i ) ) {
                            continue
                        }

                        this.add( i )
                    }
                }

        numberList.shuffle()

        val newList = setPickNumber.toList() + numberList.subList( 0 , 6 -setPickNumber.size )

        return newList.sorted()
    }

    private fun setNumberBackground( number : Int , textView: TextView ) {

        when( number ) {

            in 1..10 -> textView.background = ContextCompat.getDrawable( this , R.drawable.circle_yellow )
            in 11..20 -> textView.background = ContextCompat.getDrawable( this , R.drawable.circle_blue )
            in 21..30 -> textView.background = ContextCompat.getDrawable( this , R.drawable.circle_red )
            in 31..40 -> textView.background = ContextCompat.getDrawable( this , R.drawable.circle_gray )
            else -> textView.background = ContextCompat.getDrawable( this , R.drawable.circle_blue )
        }
    }
}