package ru.bulat.mukhutdinov.sample.infrastructure.common.network.di

import com.tumblr.jumblr.JumblrClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.infrastructure.common.network.ApiProperties
import java.util.Properties

object NetworkInjectionModule {

    val module = module {
        single {
            val apiProperties = get<ApiProperties>()

            // workaround to satisfy strict mode https://github.com/square/okhttp/issues/3537

            val client = JumblrClient(apiProperties.consumerKey, apiProperties.consumerSecret)
            client.setToken(apiProperties.token, apiProperties.tokenSecret)

            return@single client
        }

        single {
            val properties = Properties()
            val context = androidContext()
            val assetManager = context.assets
            val inputStream = assetManager.open("api.properties")
            properties.load(inputStream)

            return@single ApiProperties(
                consumerKey = properties.getProperty("consumerKey"),
                consumerSecret = properties.getProperty("consumerSecret"),
                token = properties.getProperty("token"),
                tokenSecret = properties.getProperty("tokenSecret"),
                apiKey = properties.getProperty("apiKey")
            )
        }
    }
}