package me.newbly.camyomi.presenter

import android.graphics.Bitmap
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.TestDataBuilder
import me.newbly.camyomi.domain.usecase.AddBookmarkUseCase
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import me.newbly.camyomi.domain.usecase.SaveToRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.ScannerContract
import me.newbly.camyomi.presentation.ui.ScannerPresenter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.withSettings

@RunWith(MockitoJUnitRunner::class)
class ScannerPresenterTest : BaseTest() {
    @Mock
    private lateinit var view: ScannerContract.View

    @Mock
    private lateinit var recognizeTextUseCase: RecognizeTextUseCase

    @Mock
    private lateinit var fetchDefinitionsUseCase: FetchDefinitionsUseCase

    @Mock
    private lateinit var saveToRecentlyScannedUseCase: SaveToRecentlyScannedUseCase

    @Mock
    private lateinit var addBookmarkUseCase: AddBookmarkUseCase

    private lateinit var presenter: ScannerPresenter

    @Before
    override fun setUp() {
        super.setUp()

        presenter = ScannerPresenter(
            view,
            recognizeTextUseCase,
            fetchDefinitionsUseCase,
            saveToRecentlyScannedUseCase,
            addBookmarkUseCase
        )
    }

    @Test
    fun `when scan fab clicked expect view toggles fab menu`(): Unit = runBlocking {
        doNothing()
            .`when`(view).toggleFabMenu()

        presenter.onScanFabClicked()

        verify(view).toggleFabMenu()
    }

    @Test
    fun `when camera button clicked expect view launches camera`(): Unit = runBlocking {
        doNothing()
            .`when`(view).launchCamera()

        presenter.onCameraButtonClicked()

        verify(view).launchCamera()
    }

    @Test
    fun `when image picker button clicked expect view launches media picker`(): Unit = runBlocking {
        doNothing()
            .`when`(view).launchImagePicker()

        presenter.onImagePickerButtonClicked()

        verify(view).launchImagePicker()
    }

    @Test
    fun `when bookmark button clicked expect successful adding of bookmark`(): Unit = runBlocking {
        val id = 1

        // for some reason, thenReturn() and doReturn() throws java.lang.ClassCastException
        // as it gets confused to cast the Result's type (which it shouldn't be doing at all)
        // ...therefore we're using doAnswer()
        doAnswer { invocation ->
            val receivedId = invocation.arguments[0]
            assertThat(receivedId, `is`(id))

            Result.success(true)
        }.`when`(addBookmarkUseCase).invoke(anyInt())

        val result = presenter.onBookmarkButtonClicked(id)

        verify(addBookmarkUseCase).invoke(id)
        assertThat(result, `is`(true))
    }

    @Test
    fun `when image captured expect show recognized text`(): Unit = runBlocking {
        val dummyImage = mock<Bitmap>(withSettings(lenient = true))
        val expectedText = SAMPLE_TEXT.getSentence()
        val expectedWords = SAMPLE_TEXT.getWords()

        doNothing().`when`(view).showRecognizedText(any())

        doAnswer { invocation ->
            val receivedString = invocation.arguments[0]
            assertThat(receivedString, `is`(expectedText))

            return@doAnswer Result.success(true)
        }.`when`(saveToRecentlyScannedUseCase).invoke(any())
        doAnswer { invocation ->
            val receivedImage = invocation.arguments[0]
            assertThat(receivedImage, `is`(dummyImage))

            return@doAnswer Result.success(expectedText)
        }.`when`(recognizeTextUseCase).invoke(any())

        presenter.onImageCaptured(dummyImage)

        verify(view).showRecognizedText(expectedWords)
    }

    @Test
    fun `when word selected expect show definitions of word`(): Unit = runBlocking {
        val selectedWord = SAMPLE_TEXT.getWords()[0]
        val expectedDefinitions = DEFINITION_LIST

        doNothing().`when`(view).showDefinitions(any())

        doAnswer { invocation ->
            val receivedWord = invocation.arguments[0]
            assertThat(receivedWord, `is`(selectedWord.baseForm))

            return@doAnswer Result.success(expectedDefinitions)
        }.`when`(fetchDefinitionsUseCase).invoke(any())

        presenter.onWordSelected(selectedWord.baseForm)

        verify(view).showDefinitions(expectedDefinitions)
    }

    @Test
    fun `when recently scanned text passed expect show recent scan text`(): Unit = runBlocking {
        val passedText = SAMPLE_TEXT.getSentence()
        val expectedWords = SAMPLE_TEXT.getWords()

        doNothing().`when`(view).showRecognizedText(any())

        presenter.loadPassedArgs(passedText)

        verify(view).showRecognizedText(expectedWords)
    }

    companion object {
        private val SAMPLE_TEXT = TestDataBuilder.buildJapaneseText()
        private val SAMPLE_DEFINITION = TestDataBuilder.buildDefinition()
        private val DEFINITION_LIST = listOf(
            SAMPLE_DEFINITION,
            SAMPLE_DEFINITION.copy(id = 1579111),
            SAMPLE_DEFINITION.copy(id = 1579112)
        )
    }
}
