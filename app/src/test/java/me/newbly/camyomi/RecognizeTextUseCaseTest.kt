package me.newbly.camyomi

import android.graphics.Bitmap
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.withSettings

@RunWith(MockitoJUnitRunner::class)
class RecognizeTextUseCaseTest {

    @Mock
    lateinit var mockRepository: TextRecognitionContract.Repository
    lateinit var recognizeTextUseCase: RecognizeTextUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        recognizeTextUseCase = RecognizeTextUseCase(mockRepository)
    }

    @Test
    fun `test recognize text from image`() = runBlocking {
        `when`(
            mockRepository.processImageForOCR(any())
        )
            .doReturn(
                Result.success("こんにちは")
            )

        val dummyImage = mock<Bitmap>(withSettings(lenient = true))
        val result = recognizeTextUseCase(dummyImage)

        assertEquals("こんにちは", result.getOrNull())
    }

}