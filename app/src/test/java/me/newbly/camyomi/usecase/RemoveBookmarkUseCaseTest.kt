package me.newbly.camyomi.usecase

import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.domain.usecase.RemoveBookmarkUseCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RemoveBookmarkUseCaseTest : BaseTest() {
    private lateinit var removeBookmarkUseCase: RemoveBookmarkUseCase

    @Before
    override fun setUp() {
        super.setUp()
        removeBookmarkUseCase = RemoveBookmarkUseCase(mockAppRepository)
    }

    @Test
    fun `given valid id when removing bookmark expect successful removal`(): Unit = runBlocking {
        val id = 1

        `when`(mockAppRepository.removeBookmark(id))
            .thenReturn(Result.success(true))

        val hasRemovedBookmark = removeBookmarkUseCase(id).getOrNull()

        verify(mockAppRepository).removeBookmark(id)
        assertThat(hasRemovedBookmark, `is`(true))
    }
}
