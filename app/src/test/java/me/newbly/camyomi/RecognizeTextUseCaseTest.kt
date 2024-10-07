package me.newbly.camyomi

import android.graphics.Bitmap
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.withSettings

@RunWith(MockitoJUnitRunner::class)
class RecognizeTextUseCaseTest : BaseTest() {
    private lateinit var recognizeTextUseCase: RecognizeTextUseCase

    @Before
    override fun setUp() {
        recognizeTextUseCase = RecognizeTextUseCase(mockTextRecognitionRepository)
    }

    @Test
    fun `recognize text from image`() = runBlocking {
        val dummyImage = mock<Bitmap>(withSettings(lenient = true))

        `when`(mockTextRecognitionRepository.processImageForOCR(dummyImage))
            .thenReturn(Result.success("こんにちは"))

        val result = recognizeTextUseCase(dummyImage).getOrNull()

        verify(mockTextRecognitionRepository).processImageForOCR(dummyImage)
        assertEquals("こんにちは", result)
    }
}