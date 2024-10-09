package me.newbly.camyomi.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Result>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(params: Params): Result =
        withContext(dispatcher) {
            execute(params)
        }

    protected abstract suspend fun execute(params: Params): Result
}
