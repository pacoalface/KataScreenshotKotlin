package com.karumi.ui.view

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.facebook.testing.screenshot.Screenshot
import com.github.salomonbrys.kodein.Kodein
import com.karumi.asApp
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) {
    @Rule
    @JvmField
    val testRule: ActivityTestRule<T> = ActivityTestRule(clazz, true, false)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.asApp()
        app.resetInjection()
        app.overrideModule = testDependencies
    }

    fun record(activity: Activity) {
        Screenshot.snapActivity(activity).record()
    }

    fun startActivity(): Activity {
        return testRule.launchActivity(null)
    }

    abstract val testDependencies: Kodein.Module
}