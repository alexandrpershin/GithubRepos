package com.alexpershin.githubrepos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexpershin.githubrepos.utils.Constants
import com.google.gson.annotations.SerializedName

/**
 *  [GithubRepositoryEntity] is a model class for obtaining Github repositories from server
 *  [GithubRepositoryEntity] can be also used for data persistence using Room database
* */

@Entity(tableName = Constants.REPOSITORY_TABLE)
data class GithubRepositoryEntity(

    @PrimaryKey(autoGenerate = false) var id: Int = 0,

    var owner: Owner? = Owner(),

    var permissions: Permissions? = Permissions(),

    var license: License? = License(),

    var language: String? = "",

    var name: String? = "",

    var archived: Boolean = false,

    var description: String? = "",

    var disabled: Boolean = false,

    var fork: Boolean = false,

    var forks: Int = 0,

    var size: Int = 0,

    var url: String? = "",

    var watchers: Int = 0,

    @SerializedName("archive_url")
    var archiveUrl: String? = "",

    @SerializedName("assignees_url")
    var assigneesUrl: String? = "",

    @SerializedName("blobs_url")
    var blobsUrl: String? = "",

    @SerializedName("branches_url")
    var branchesUrl: String? = "",

    @SerializedName("clone_url")
    var cloneUrl: String? = "",

    @SerializedName("collaborators_url")
    var collaboratorsUrl: String? = "",

    @SerializedName("comments_url")
    var commentsUrl: String? = "",

    @SerializedName("commits_url")
    var commitsUrl: String? = "",

    @SerializedName("compare_url")
    var compareUrl: String? = "",

    @SerializedName("contents_url")
    var contentsUrl: String? = "",

    @SerializedName("contributors_url")
    var contributorsUrl: String? = "",

    @SerializedName("created_at")
    var createdAt: String? = "",

    @SerializedName("default_branch")
    var defaultBranch: String? = "",

    @SerializedName("deployments_url")
    var deploymentsUrl: String? = "",

    @SerializedName("downloads_url")
    var downloadsUrl: String? = "",

    @SerializedName("events_url")
    var eventsUrl: String? = "",

    @SerializedName("forks_count")
    var forksCount: Int = 0,

    @SerializedName("forks_url")
    var forksUrl: String? = "",

    @SerializedName("full_name")
    var fullName: String? = "",

    @SerializedName("git_commits_url")
    var gitCommitsUrl: String? = "",

    @SerializedName("git_refs_url")
    var gitRefsUrl: String? = "",

    @SerializedName("git_tags_url")
    var gitTagsUrl: String? = "",

    @SerializedName("git_url")
    var gitUrl: String? = "",

    @SerializedName("has_downloads")
    var hasDownloads: Boolean = false,

    @SerializedName("has_issues")
    var hasIssues: Boolean = false,

    @SerializedName("has_pages")
    var hasPages: Boolean = false,

    @SerializedName("has_projects")
    var hasProjects: Boolean = false,

    @SerializedName("has_wiki")
    var hasWiki: Boolean = false,

    @SerializedName("homepage")
    var homepage: String? = "",

    @SerializedName("hooks_url")
    var hooks_url: String? = "",

    @SerializedName("html_url")
    var htmlUrl: String? = "",

    @SerializedName("issue_comment_url")
    var issueComment_url: String? = "",

    @SerializedName("issue_events_url")
    var issueEvents_url: String? = "",

    @SerializedName("issues_url")
    var issuesUrl: String? = "",

    @SerializedName("keys_url")
    var keysUrl: String? = "",

    @SerializedName("labels_url")
    var labelsUrl: String? = "",

    @SerializedName("private")
    var isPrivate: Boolean = false,

    @SerializedName("svn_url")
    var svnUrl: String? = "",

    @SerializedName("tags_url")
    var tagsUrl: String? = "",

    @SerializedName("teams_url")
    var teamsUrl: String? = "",

    @SerializedName("trees_url")
    var treesUrl: String? = "",

    @SerializedName("languages_url")
    var languagesUrl: String? = "",

    @SerializedName("merges_url")
    var mergesUrl: String? = "",

    @SerializedName("milestones_url")
    var milestonesUrl: String? = "",

    @SerializedName("mirror_url")
    var mirrorUrl: String? = "",

    @SerializedName("node_id")
    var nodeId: String? = "",

    @SerializedName("notifications_url")
    var notificationsUrl: String? = "",

    @SerializedName("open_issues")
    var openIssues: Int = 0,

    @SerializedName("open_issues_count")
    var openIssuesCount: Int = 0,

    @SerializedName("pulls_url")
    var pullsUrl: String? = "",

    @SerializedName("pushed_at")
    var pushedAt: String? = "",

    @SerializedName("releases_url")
    var releasesUrl: String? = "",

    @SerializedName("ssh_url")
    var sshUrl: String? = "",

    @SerializedName("stargazers_count")
    var stargazersCount: Int = 0,

    @SerializedName("stargazers_url")
    var stargazersUrl: String? = "",

    @SerializedName("statuses_url")
    var statusesUrl: String? = "",

    @SerializedName("subscribers_url")
    var subscribersUrl: String? = "",

    @SerializedName("subscription_url")
    var subscriptionUrl: String? = "",

    @SerializedName("updated_at")
    var updatedAt: String? = "",

    @SerializedName("watchers_count")
    var watchersCount: Int = 0
)

data class License(

    var key: String? = "",

    var url: String? = "",

    var name: String? = "",

    @SerializedName("node_id")
    var nodeId: String? = "",

    @SerializedName("spdx_id")
    var spdxId: String? = ""
)

data class Owner(

    var id: Int = 0,

    var login: String? = "",

    var type: String? = "",

    var url: String? = "",

    @SerializedName("avatar_url")
    var avatarUrl: String? = "",

    @SerializedName("events_url")
    var eventsUrl: String? = "",

    @SerializedName("followers_url")
    var followersUrl: String? = "",

    @SerializedName("following_url")
    var followingUrl: String? = "",

    @SerializedName("gists_url")
    var gistsUrl: String? = "",

    @SerializedName("gravatar_id")
    var gravatarId: String? = "",

    @SerializedName("html_url")
    var htmlUrl: String? = "",

    @SerializedName("node_id")
    var nodeId: String? = "",

    @SerializedName("organizations_url")
    var organizationsUrl: String? = "",

    @SerializedName("received_events_url")
    var receivedEventsUrl: String? = "",

    @SerializedName("repos_url")
    var reposUrl: String? = "",

    @SerializedName("site_admin")
    var siteAdmin: Boolean = false,

    @SerializedName("starred_url")
    var starredUrl: String? = "",

    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String? = ""
)

data class Permissions(

    var admin: Boolean = false,

    var pull: Boolean = false,

    var push: Boolean = false
)