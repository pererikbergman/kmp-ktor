package post.domain.usecase

import core.Result
import core.network.DataError
import kotlinx.coroutines.flow.first
import post.domain.model.Post
import post.domain.model.PostRepository

class GetAllPostsUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(): Result<List<Post>, DataError> {
        return postRepository.fetchAll().first()
    }
}
