package com.example.newsApp.ui.theme

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsApp.ViewModel.NewsViewModel

@Composable
fun HomeScreen(viewModel: NewsViewModel, navController: NavHostController) {

    val articles by viewModel.articles.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {

        CategoryBar(viewModel)

        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            items(articles) {
                ArticleItem(it, navController)
            }

        }
    }
}

@Composable
fun CategoryBar(viewModel: NewsViewModel) {

    val categoryList = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORT",
        "TECHNOLOGY",
    )
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (expanded) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                shape = CircleShape,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            expanded = false
                            if (searchQuery.isNotEmpty()) {
                                viewModel.fetchWithQuery(searchQuery)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                        )
                    }
                }
            )

        } else {
            IconButton(
                onClick = { expanded = true },

                ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        }

        categoryList.forEach { category ->
            Button(
                onClick = {
                    viewModel.fetchTopHeadlines(category)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = category
                )
            }
        }

    }
}