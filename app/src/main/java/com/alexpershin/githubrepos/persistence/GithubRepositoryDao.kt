package com.alexpershin.githubrepos.persistence

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.alexpershin.githubrepos.model.GithubRepositoryEntity

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface GithubRepositoryDao {

    @Transaction
    fun updateData(repos: List<GithubRepositoryEntity>) {
        deleteRepos()
        insertRepos(repos)
    }

    @Query("SELECT * FROM GithubRepositoryEntity ORDER BY id ASC")
    fun getAllReposDataSource(): DataSource.Factory<Int, GithubRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<GithubRepositoryEntity>)

    @Query("SELECT * from GithubRepositoryEntity")
    fun getRepos(): LiveData<List<GithubRepositoryEntity>>

    @Query("SELECT * from GithubRepositoryEntity")
    fun getAllReposSync(): List<GithubRepositoryEntity>

    @Query("DELETE FROM GithubRepositoryEntity")
    fun deleteRepos()
}