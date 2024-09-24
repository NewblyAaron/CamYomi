package me.newbly.camyomi

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SingletonInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Inject lateinit var database1: JMdictDatabase
    @Inject lateinit var database2: JMdictDatabase

    @Test
    fun testSingletonInstance() {
        assertThat(database1, `is`(database2))
    }
}