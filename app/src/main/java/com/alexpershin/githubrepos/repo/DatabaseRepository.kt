package com.alexpershin.githubrepos.repo

import androidx.paging.DataSource
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.alexpershin.githubrepos.persistence.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

/**
 * [DatabaseRepository] is repository for manipulating data of [LocalDatabase]
* */

open class DatabaseRepository(private var localDatabase: LocalDatabase) {

    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

    private fun ioThread(f: () -> Unit) {
        IO_EXECUTOR.execute(f)
    }

    fun getAllReposDataSource(): DataSource.Factory<Int, GithubRepositoryEntity> {
        return localDatabase.githubRepositoryDao().getAllReposDataSource()
    }

    suspend fun getAllReposSync(): List<GithubRepositoryEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext localDatabase.githubRepositoryDao().getAllReposSync()
        }
    }

    fun insertGithubRepos(data: List<GithubRepositoryEntity>) {
        ioThread {
            localDatabase.githubRepositoryDao().insertRepos(data)
        }
    }
}