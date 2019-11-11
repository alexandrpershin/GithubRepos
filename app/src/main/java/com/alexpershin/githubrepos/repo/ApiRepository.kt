package com.alexpershin.githubrepos.repo

import com.alexpershin.githubrepos.api.GithubApi
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.alexpershin.githubrepos.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**[]
 * [ApiRepository] is a class which fetches data from [GithubApi],
 * handles it with [ServerResponseHandler] and provides response wrapped with [ServerResult]
* */

open class ApiRepository(private val githubApi: GithubApi) {

    suspend fun getReposByOrganization(
        organization: String,
        page: Int,
        perPage: Int
    ): ServerResult<List<GithubRepositoryEntity>> {
        return withContext(Dispatchers.IO) {
            val result =
                ServerResponseHandler<List<GithubRepositoryEntity>>().getResult {
                    return@getResult githubApi.getReposByOrganization(organization, page, perPage)
                        .await()
                }

            return@withContext result
        }
    }

}