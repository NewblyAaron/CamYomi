package me.newbly.camyomi

import me.newbly.camyomi.presentation.contract.AppDbContract
import me.newbly.camyomi.presentation.contract.JMdictContract
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class BaseTest {
    @Mock
    protected lateinit var mockTextRecognitionRepository: TextRecognitionContract.Repository

    @Mock
    protected lateinit var mockAppRepository: AppDbContract.Repository

    @Mock
    protected lateinit var mockJMdictRepository: JMdictContract.Repository

    @Before
    open fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
}