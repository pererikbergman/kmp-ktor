package post.data

import post.domain.model.Post

fun PostDto.toPost() = Post(
    id, userId, title, body
)

fun Post.toPostDto() = PostDto(
    id, userId, title, body
)

 