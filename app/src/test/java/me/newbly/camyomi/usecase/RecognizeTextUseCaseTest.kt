package me.newbly.camyomi.usecase

import android.graphics.Bitmap
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
    fun `given valid image with text when scanning image expect recognized text`() = runBlocking {
        val dummyImage = mock<Bitmap>(withSettings(lenient = true))
        val expectedText = "こんにちは"

        `when`(mockTextRecognitionRepository.processImageForOCR(dummyImage))
            .thenReturn(Result.success(expectedText))

        val result = recognizeTextUseCase(dummyImage).getOrNull()

        verify(mockTextRecognitionRepository).processImageForOCR(dummyImage)
        assertThat(result, `is`(expectedText))
    }
}