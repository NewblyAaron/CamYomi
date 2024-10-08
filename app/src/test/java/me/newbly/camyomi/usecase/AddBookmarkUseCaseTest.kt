package me.newbly.camyomi.usecase

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.domain.usecase.AddBookmarkUseCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class AddBookmarkUseCaseTest : BaseTest() {
    private lateinit var addBookmarkUseCase: AddBookmarkUseCase

    @Before
    override fun setUp() {
        super.setUp()
        addBookmarkUseCase = AddBookmarkUseCase(mockAppRepository)
    }

    @Test
    fun `given valid id when adding bookmark expect successful adding`(): Unit = runBlocking {
        val id = 1

        `when`(mockAppRepository.addBookmark(id))
            .thenReturn(Result.success(true))

        val hasAddedBookmark = addBookmarkUseCase(id).getOrNull()

        verify(mockAppRepository).addBookmark(id)
        assertThat(hasAddedBookmark, `is`(true))
    }
}