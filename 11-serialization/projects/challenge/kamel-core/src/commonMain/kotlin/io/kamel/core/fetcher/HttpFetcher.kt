package io.kamel.core.fetcher

import io.kamel.core.DataSource
import io.kamel.core.Resource
import io.kamel.core.config.ResourceConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.request
import io.ktor.client.request.takeFrom
import io.ktor.client.request.url
import io.ktor.http.Url
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

/**
 * Fetcher that fetches [ByteReadChannel] from network using [Url].
 */
internal class HttpFetcher(private val client: HttpClient) : Fetcher<Url> {

    override val source: DataSource = DataSource.Network

    override val Url.isSupported: Boolean
        get() = protocol.name == "https" || protocol.name == "http"

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun fetch(
        data: Url,
        resourceConfig: ResourceConfig
    ): Flow<Resource<ByteReadChannel>> = channelFlow {
        val response = client.request {
            onDownload { bytesSentTotal, contentLength ->
                val progress = (bytesSentTotal.toFloat() / contentLength).coerceAtMost(1.0F)
                send(Resource.Loading(progress))
            }
            takeFrom(resourceConfig.requestData)
            url(data)
        }
        val bytes = response.body<ByteReadChannel>()
        send(Resource.Success(bytes))
    }

}