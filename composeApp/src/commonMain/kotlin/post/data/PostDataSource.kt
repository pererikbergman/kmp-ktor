package post.data

import io.ktor.http.Headers
import kotlinx.coroutines.flow.Flow
import post.domain.model.Post

interface PostDataSource {
    suspend fun getAll(): Flow<List<PostDto>>
    suspend fun create(newPost: Post): Flow<PostDto>
    suspend fun update(updatedPost: PostDto): Flow<PostDto>
    suspend fun delete(postId: Long): Flow<Boolean>
    suspend fun patch(postId: String, postPatch: Post): Flow<PostDto>
    suspend fun head(postId: String): Flow<Headers>
    suspend fun options(postId: String): Flow<Headers>
    suspend fun getById(id: Int): Flow<PostDto>
}
