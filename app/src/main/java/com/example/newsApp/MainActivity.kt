package com.example.newsApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsApp.ViewModel.NewsViewModel
import com.example.newsApp.constants.HomeScreen
import com.example.newsApp.constants.NewsArticleScreen
import com.example.newsApp.ui.theme.HomeScreen
import com.example.newsApp.ui.theme.MapLibreViewTheme
import com.example.newsApp.ui.theme.NewsDetailScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        setContent {
            val navController = rememberNavController()
            MapLibreViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(
                            "NEWS NOW!",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif
                        )

                        NavHost(startDestination = HomeScreen, navController = navController) {
                            composable<HomeScreen> { HomeScreen(viewModel,navController) }
                            composable<NewsArticleScreen> {
                                val args = it.toRoute<NewsArticleScreen>()
                                NewsDetailScreen(args.url) }
                        }
                    }
                }
            }
        }
    }
}



