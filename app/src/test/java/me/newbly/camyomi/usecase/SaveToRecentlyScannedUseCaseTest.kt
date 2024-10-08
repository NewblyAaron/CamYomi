package me.newbly.camyomi.usecase

import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.domain.usecase.SaveToRecentlyScannedUseCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SaveToRecentlyScannedUseCaseTest : BaseTest() {
    private lateinit var saveToRecentlyScannedUseCase: SaveToRecentlyScannedUseCase

    @Before
    override fun setUp() {
        super.setUp()
        saveToRecentlyScannedUseCase = SaveToRecentlyScannedUseCase(mockAppRepository)
    }

    @Test
    fun `when save text to recently scanned expect successful save`(): Unit = runBlocking {
        val textToSave = "言葉"

        `when`(mockAppRepository.saveToRecentlyScanned(textToSave))
            .thenReturn(Result.success(true))

        val hasSavedToRecentScans = saveToRecentlyScannedUseCase(textToSave).getOrNull()

        verify(mockAppRepository).saveToRecentlyScanned(textToSave)
        assertThat(hasSavedToRecentScans, `is`(true))
    }
}