package di

import core.data.remote.network.HttpClientFactory
import post.PostScreenUseCases
import post.PostScreenViewModel
import post.data.PostDataSourceKtor
import post.data.PostRepositoryKtor
import post.domain.usecase.GetAllPostsUseCase

fun createViewModel(): PostScreenViewModel {
    return PostScreenViewModel(
        PostScreenUseCases(
            GetAllPostsUseCase(
                PostRepositoryKtor(
                    PostDataSourceKtor(HttpClientFactory().createHttpClient())
                )
            )
        )
    )
}