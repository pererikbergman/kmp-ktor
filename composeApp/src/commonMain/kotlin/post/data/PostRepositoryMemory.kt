package post.data

import core.Result
import core.network.DataError
import kotlinx.coroutines.flow.flow
import post.domain.model.Post
import post.domain.model.PostRepository

class PostRepositoryMemory() : PostRepository {

    private val posts = mutableListOf(
        Post(1, 2, "Discovering Space", "The cosmos is vast and beautiful."),
        Post(2, 2, "Java vs Python", "Comparing two powerful languages."),
        Post(3, 3, "Gardening Tips", "Learn to grow your own herbs."),
        Post(4, 3, "Mysteries of History", "Exploring ancient civilizations."),
        Post(5, 4, "Healthy Eating", "Balanced diets for a busy life."),
        Post(6, 5, "Marathon Training", "Preparing for your first race."),
        Post(7, 6, "Photography Basics", "Capture stunning landscapes."),
        Post(8, 6, "Future of AI", "How AI is changing our world."),
        Post(9, 7, "World Cuisine", "Exploring flavors from around the globe."),
        Post(10, 9, "Space Exploration", "Journey to the stars and beyond.")
    )

    override fun fetchAll() = flow<Result<List<Post>, DataError>> {
        emit(Result.Success(posts))
    }

    override fun fetchById(id: Int) = flow<Result<Post, DataError>> {
        val post = posts.find { it.id == id.toLong() }
        post?.let {
            emit(Result.Success(it))
        } ?: run {
            emit(Result.Error(DataError.DATA_NOT_FOUND))
        }
    }

    override fun create(newPost: Post) = flow<Result<Post, DataError>> {
        posts.add(newPost.copy(id = (posts.maxOfOrNull { it.id } ?: 0) + 1))  // Ensure unique ID
        emit(Result.Success(posts.last()))
    }

    override fun update(existingPost: Post) = flow<Result<Post, DataError>> {
        val index = posts.indexOfFirst { it.id == existingPost.id }
        if (index != -1) {
            posts[index] = existingPost
            emit(Result.Success(existingPost))
        } else {
            emit(Result.Error(DataError.DATA_NOT_FOUND))
        }
    }

    override fun delete(postToRemove: Post) = flow<Result<Boolean, DataError>> {
        val result = posts.removeAll { it.id == postToRemove.id }
        if (result) {
            emit(Result.Success(true))
        } else {
            emit(Result.Error(DataError.UNKNOWN))
        }
    }
}