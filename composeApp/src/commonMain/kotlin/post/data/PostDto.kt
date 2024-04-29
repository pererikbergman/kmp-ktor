package post.data

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
)