package me.newbly.camyomi

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import me.newbly.camyomi.data.local.app.AppDatabase
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import me.newbly.camyomi.presentation.contract.AppDbContract
import me.newbly.camyomi.presentation.contract.JMdictContract
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class SingletonUnitTest : HiltBaseTest() {
    @Inject
    lateinit var jmdictDatabase1: JMdictDatabase
    @Inject
    lateinit var jmdictDatabase2: JMdictDatabase
    @Inject
    lateinit var appDatabase1: AppDatabase
    @Inject
    lateinit var appDatabase2: AppDatabase

    @Test
    fun `databases are singleton`() {
        assertThat(jmdictDatabase1, `is`(jmdictDatabase2))
        assertThat(appDatabase1, `is`(appDatabase2))
    }

    @Inject
    lateinit var appRepository1: AppDbContract.Repository
    @Inject
    lateinit var appRepository2: AppDbContract.Repository
    @Inject
    lateinit var jmdictRepository1: JMdictContract.Repository
    @Inject
    lateinit var jmdictRepository2: JMdictContract.Repository

    @Test
    fun `repositories are singleton`() {
        assertThat(appRepository1, `is`(appRepository2))
        assertThat(jmdictRepository1, `is`(jmdictRepository2))
    }
}