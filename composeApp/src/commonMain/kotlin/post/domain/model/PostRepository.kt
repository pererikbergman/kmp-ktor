package post.domain.model

import core.Result
import core.network.DataError
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun fetchAll(): Flow<Result<List<Post>, DataError>>
    fun fetchById(id: Int): Flow<Result<Post, DataError>>
    fun create(newPost: Post): Flow<Result<Post, DataError>>
    fun update(existingPost: Post): Flow<Result<Post, DataError>>
    fun delete(postToRemove: Post): Flow<Result<Boolean, DataError>>
}
