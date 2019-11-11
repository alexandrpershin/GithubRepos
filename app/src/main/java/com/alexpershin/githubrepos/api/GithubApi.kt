package com.alexpershin.githubrepos.api

 import android.content.Context
import com.alexpershin.githubrepos.BuildConfig
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.alexpershin.githubrepos.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GithubApi {

    companion object {
        private val gson: Gson = GsonBuilder().create()

        private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

        private fun provideRetrofit(context: Context): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(provideOkHttpClient(context))
                .baseUrl(BASE_URL)
                .build()
        }

        private fun provideOkHttpClient(context: Context): OkHttpClient {

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()

            // print in console all request/response
            val consoleInterceptor = HttpLoggingInterceptor().also {
                it.level = when (BuildConfig.DEBUG) {
                    true -> HttpLoggingInterceptor.Level.BODY
                    else -> HttpLoggingInterceptor.Level.NONE
                }
            }

            builder.connectTimeout(5, TimeUnit.SECONDS)
            builder.readTimeout(5, TimeUnit.SECONDS)
            builder.addInterceptor(consoleInterceptor)

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(ChuckInterceptor(context)) //Very cool interceptor library
            }

            return builder.build()
        }

        fun create(context: Context): GithubApi {
            val retrofit = provideRetrofit(context)
            return retrofit.create(GithubApi::class.java)
        }
    }

    @GET("orgs/{organization}/repos")
    fun getReposByOrganization(
        @Path("organization") organization: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Deferred<Response<List<GithubRepositoryEntity>>>

}