package post.data

import core.Result
import core.network.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import post.domain.model.Post
import post.domain.model.PostRepository

class PostRepositoryKtor(
    private val dataSource: PostDataSource
) : PostRepository {

    private inline fun <T, R> fetchResult(
        crossinline fetch: suspend () -> Flow<T>,
        crossinline transform: (T) -> R
    ): Flow<Result<R, DataError>> = flow {
        fetch().catch {
            emit(Result.Error(DataError.UNKNOWN))
        }.collect { result ->
            emit(Result.Success(transform(result)))
        }
    }

    override fun fetchAll() = fetchResult(
        fetch = { dataSource.getAll() },
        transform = { dtos -> dtos.map { it.toPost() } }
    )

    override fun fetchById(id: Int) = flow<Result<Post, DataError>> {
        dataSource.getById(id).catch {
            emit(Result.Error(DataError.UNKNOWN))
        }.collect { dto ->
            emit(Result.Success(dto.toPost()))
        }
    }

    override fun create(newPost: Post) = flow<Result<Post, DataError>> {
        dataSource.create(newPost).catch {
            emit(Result.Error(DataError.UNKNOWN))
        }.collect { dto ->
            emit(Result.Success(dto.toPost()))
        }
    }

    override fun update(existingPost: Post) = flow<Result<Post, DataError>> {
        dataSource.update(existingPost.toPostDto()).catch {
            emit(Result.Error(DataError.UNKNOWN))
        }.collect { dto ->
            emit(Result.Success(dto.toPost()))
        }
    }

    override fun delete(postToRemove: Post) = flow<Result<Boolean, DataError>> {
        dataSource.delete(postToRemove.id).catch {
            emit(Result.Error(DataError.UNKNOWN))
        }.collect { success ->
            emit(Result.Success(success))
        }
    }

}