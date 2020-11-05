package com.github.sigute.colorchangedemo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    //rule not used for allow koin module loading first
    // @get:Rule
    // val activityRule = ActivityScenarioRule(MainActivity::class.java)

    //mock provider is mockito
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mock(clazz.java) }

    //for live data
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var randomColorViewModel: RandomColorViewModel

    private lateinit var koinModule: Module

    private val testColorRes = R.color.purple_700
    private val testColorText = "#FF3700B3"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val randomColorLiveData = MutableLiveData<Pair<Int, String>>()
        `when`(randomColorViewModel.randomColorLiveData).thenReturn(randomColorLiveData)

        koinModule = module {
            viewModel { randomColorViewModel }
        }
        loadKoinModules(koinModule)

        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun after() {
        unloadKoinModules(koinModule)
    }

    @Test
    fun testColorChange() {
        `when`(randomColorViewModel.colorTapped()).thenAnswer {
            randomColorViewModel.randomColorLiveData.value = Pair(testColorRes, testColorText)
            return@thenAnswer Unit
        }

        onView(withId(R.id.changeColorButton))
            .check(matches(isDisplayed()))
            .perform(click())
        verify(randomColorViewModel).colorTapped()

        onView(withId(R.id.colorName)).check(
            matches(allOf(isDisplayed(), withText(testColorText)))
        )
        onView(withId(R.id.colorSquare)).check(
            matches(allOf(isDisplayed(), withBackgroundColor(testColorRes)))
        )
        onView(withText(getSnackbarText(testColorText))).check(
            matches(isDisplayed())
        )
    }

    private fun getSnackbarText(colorName: String): String {
        val context: Context = ApplicationProvider.getApplicationContext()
        return context.resources.getString(R.string.color_changed_to_x, colorName)
    }

    @Test
    fun testMultipleColorChanges() {
        onView(withId(R.id.changeColorButton))
            .check(matches(isDisplayed()))
            .perform(click(), click(), click(), click(), click())
        verify(randomColorViewModel, times(5)).colorTapped()
    }
}
