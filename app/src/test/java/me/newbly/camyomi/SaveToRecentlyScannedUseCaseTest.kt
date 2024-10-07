package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.SaveToRecentlyScannedUseCase
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
    fun `save text to recently scanned`(): Unit = runBlocking {
        val textToSave = "言葉"

        `when`(mockAppRepository.saveToRecentlyScanned(textToSave))
            .thenReturn(Result.success(true))

        val hasSavedToRecentScans = saveToRecentlyScannedUseCase(textToSave)

        verify(mockAppRepository).saveToRecentlyScanned(textToSave)
        assertEquals(true, hasSavedToRecentScans)
    }
}