package com.github.sigute.colorchangedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val randomColorViewModel: RandomColorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeColorButton.setOnClickListener {
            randomColorViewModel.colorTapped()
        }
        randomColorViewModel.randomColorLiveData.observe(this, { color ->
            Snackbar.make(mainActivityRootView, getString(R.string.color_changed_to_x, color.second), Snackbar.LENGTH_SHORT).show()
            colorSquare.setBackgroundColor(color.first)
            colorName.text = color.second
        })
    }
}
