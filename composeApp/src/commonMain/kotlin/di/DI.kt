package di

import post.PostScreenUseCases
import post.PostScreenViewModel
import post.data.PostRepositoryMemory
import post.domain.usecase.GetAllPostsUseCase

fun createViewModel(): PostScreenViewModel {
    return PostScreenViewModel(
        PostScreenUseCases(
            GetAllPostsUseCase(
                PostRepositoryMemory()
            )
        )
    )
}