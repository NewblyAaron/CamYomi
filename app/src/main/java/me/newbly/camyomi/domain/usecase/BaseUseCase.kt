package me.newbly.camyomi.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Result> {
    suspend operator fun invoke(params: Params): Result =
        withContext(Dispatchers.IO) {
            execute(params)
        }

    protected abstract suspend fun execute(params: Params): Result
}