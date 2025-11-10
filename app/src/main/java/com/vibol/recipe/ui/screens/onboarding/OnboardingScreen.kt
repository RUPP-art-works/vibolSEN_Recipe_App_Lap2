// In ui/screens/onboarding/OnboardingScreen.kt
package com.vibol.recipe.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibol.recipe.R // <-- IMPORT YOUR R FILE
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    // 1. THE FIRST BIG CHANGE IS HERE: Add the imageRes to your page data
    val pages = listOf(
        OnboardingPage(
            title = "Welcome to Recipe Finder!",
            description = "Discover thousands of delicious recipes from all around the globe.",
            imageRes = R.drawable.onboarding_1 // <-- Make sure this matches your file name
        ),
        OnboardingPage(
            title = "Save Your Favorites",
            description = "Found a recipe you love? Save it to your personal cookbook for easy access anytime.",
            imageRes = R.drawable.onboarding_2 // <-- Make sure this matches your file name
        ),
        OnboardingPage(
            title = "Explore by Category",
            description = "Looking for something specific? Filter recipes by category to find the perfect meal.",
            imageRes = R.drawable.onboarding_3 // <-- Make sure this matches your file name
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            OnboardingPageUI(page = pages[pageIndex])
        }

        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                val color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }

        Box(modifier = Modifier.padding(24.dp)) {
            if (pagerState.currentPage == pages.lastIndex) {
                Button(
                    onClick = onFinished,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Started")
                }
            } else {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            }
        }
    }
}


@Composable
fun OnboardingPageUI(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. THE SECOND BIG CHANGE IS HERE: Add the Image composable
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier
                .fillMaxWidth(0.8f) // Take up 80% of the width
                .aspectRatio(1f) // Make it a square
                .clip(CircleShape) // You can make it a circle or another shape
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}