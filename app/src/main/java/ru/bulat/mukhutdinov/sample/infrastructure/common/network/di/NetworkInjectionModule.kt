package ru.bulat.mukhutdinov.sample.infrastructure.common.network.di

import android.content.Context
import com.tumblr.jumblr.JumblrClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.sample.infrastructure.common.network.ApiProperties
import java.util.Properties

object NetworkInjectionModule {

    val module = Kodein.Module(NetworkInjectionModule::class.java.name) {

        bind<JumblrClient>() with singleton {
            val apiProperties = instance<ApiProperties>()

            val client = JumblrClient(apiProperties.consumerKey, apiProperties.consumerSecret)
            client.setToken(apiProperties.token, apiProperties.tokenSecret)

            return@singleton client
        }

        bind<ApiProperties>() with provider {
            val properties = Properties()
            val context = instance<Context>()
            val assetManager = context.assets
            val inputStream = assetManager.open("api.properties")
            properties.load(inputStream)

            return@provider ApiProperties(
                consumerKey = properties.getProperty("consumerKey"),
                consumerSecret = properties.getProperty("consumerSecret"),
                token = properties.getProperty("token"),
                tokenSecret = properties.getProperty("tokenSecret"),
                apiKey = properties.getProperty("apiKey")
            )
        }
    }
}