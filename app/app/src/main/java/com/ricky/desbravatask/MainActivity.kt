package com.ricky.desbravatask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.navigation.AppNav
import com.ricky.desbravatask.ui.theme.DesbravaTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreUtil: DataStoreUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataStoreUtil = DataStoreUtil(applicationContext)
        var darkMode by mutableStateOf(false)

        lifecycleScope.launch {
            dataStoreUtil.getTheme().collect {
                darkMode = it
            }
        }

        setContent {
            DesbravaTaskTheme(darkTheme = darkMode) {
                AppNav()
            }
        }
    }
}
