package me.newbly.camyomi

import android.graphics.Bitmap
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import org.junit.Before
import org.junit.Test


class RecognizeTextUseCaseTest {

    @MockK
    lateinit var mockRepository: TextRecognitionContract.Repository

    @InjectMockKs
    lateinit var recognizeTextUseCase: RecognizeTextUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test recognize text from image`() = runBlocking {
        coEvery { mockRepository.processImageForOCR(any()) } returns Result.success("こんにちは")

        val dummyImage = mockk<Bitmap>(relaxed = true)
        val result = recognizeTextUseCase(dummyImage)

        assertEquals("こんにちは", result.getOrNull())
    }

}