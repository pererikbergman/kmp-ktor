package post.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import post.domain.model.Post

class PostDataSourceKtor(
    private val client: HttpClient
) : PostDataSource {

    private val BASE_URL: String = "jsonplaceholder.typicode.com"

    private fun HttpRequestBuilder.commonHeaders() {
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "your_token")
            append(HttpHeaders.UserAgent, "ktor client")
        }
    }

    private fun HttpRequestBuilder.authenticatedJsonHeaders() {
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            append(HttpHeaders.Authorization, "your_token")
        }
    }

    /**
     * GET Request
     * A GET request is used to retrieve data from a server. Here's how you can implement it:
     * **/
    override suspend fun getAll(): Flow<List<PostDto>> = flow {
        try {
            val response: HttpResponse = client.get {
                commonHeaders()
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path("posts")
                }
            }

            if (response.status.isSuccess()) {
                emit(response.body())
            } else {
                throw Exception("Failed to fetch posts: ${response.status.description}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * GET Request
     * A GET request is used to retrieve data from a server. Here's how you can implement it:
     * **/
    override suspend fun getById(id: Int): Flow<PostDto> = flow {
        try {
            val response: HttpResponse = client.get {
                commonHeaders()
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path("posts/$id")
                }
            }

            if (response.status.isSuccess()) {
                emit(response.body())
            } else {
                throw Exception("Failed to fetch post by id ($id): ${response.status.description}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * POST Request
     * A POST request is used to send data to the server. Here's an example of how to send a JSON object:
     * **/
    @OptIn(InternalAPI::class)
    override suspend fun create(newPost: Post): Flow<PostDto> = flow {
        try {
            val response: HttpResponse = client.post {
                authenticatedJsonHeaders()
                url("$BASE_URL/posts")
                body = newPost
            }

            if (response.status.isSuccess()) {
                emit(response.body())
            } else {
                throw Exception("Failed to create post: ${response.status.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * PUT Request
     * A PUT request is often used to update a resource entirely:
     * **/
    @OptIn(InternalAPI::class)
    override suspend fun update(updatedPost: PostDto): Flow<PostDto> = flow {
        try {
            val response: HttpResponse = client.put {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    append(HttpHeaders.Authorization, "your_token")
                }
                url("$BASE_URL/posts/${updatedPost.id}")
                body = updatedPost
            }

            if (response.status.isSuccess()) {
                emit(response.body())
            } else {
                throw Exception("Failed to update post: ${response.status.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * PUT Request
     * A DELETE request is used to remove a resource:
     * **/
    override suspend fun delete(postId: Long): Flow<Boolean> = flow {
        try {
            val response: HttpResponse = client.delete {
                authenticatedJsonHeaders()
                url("$BASE_URL/posts/$postId")
            }
            emit(response.status.isSuccess())
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * PATCH Request
     * A PATCH request is used for partial updates:
     **/
    @OptIn(InternalAPI::class)
    override suspend fun patch(postId: String, postPatch: Post): Flow<PostDto> = flow {
        try {
            val response: HttpResponse = client.patch {
                authenticatedJsonHeaders()
                url("$BASE_URL/posts/$postId")
                body = postPatch
            }

            if (response.status.isSuccess()) {
                emit(response.body())
            } else {
                throw Exception("Failed to patch post: ${response.status.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * HEAD Request
     * A HEAD request is similar to GET but retrieves only the headers:
     * **/
    override suspend fun head(postId: String): Flow<Headers> = flow {
        try {
            val response: HttpResponse = client.head {
                authenticatedJsonHeaders()
                url("$BASE_URL/posts/$postId")
            }

            if (response.status.isSuccess()) {
                emit(response.headers)
            } else {
                throw Exception("Failed to get the headers for post by id: ${response.status.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * OPTIONS Request
     * An OPTIONS request is used to describe communication options:
     * **/
    override suspend fun options(postId: String): Flow<Headers> = flow {
        try {
            val response: HttpResponse = client.options {
                headers {
                    append(HttpHeaders.Authorization, "your_token")
                }
                url("$BASE_URL/posts/$postId")
            }

            if (response.status.isSuccess()) {
                emit(response.headers)
            } else {
                throw Exception("Failed to get options for post by id: ${response.status.value}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

}