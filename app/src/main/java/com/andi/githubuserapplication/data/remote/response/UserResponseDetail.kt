package com.andi.githubuserapplication.data.remote.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponseDetail(
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,
    @SerializedName("bio")
    @Expose
    val bio: Any,
    @SerializedName("blog")
    @Expose
    val blog: String,
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("email")
    @Expose
    val email: Any,
    @SerializedName("events_url")
    @Expose
    val eventsUrl: String,
    @SerializedName("followers")
    @Expose
    val followers: Int,
    @SerializedName("followers_url")
    @Expose
    val followersUrl: String,
    @SerializedName("following")
    @Expose
    val following: Int,
    @SerializedName("following_url")
    @Expose
    val followingUrl: String,
    @SerializedName("gists_url")
    @Expose
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    @Expose
    val gravatarId: String,
    @SerializedName("hireable")
    @Expose
    val hireable: Any,
    @SerializedName("html_url")
    @Expose
    val htmlUrl: String,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("location")
    @Expose
    val location: String,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("node_id")
    @Expose
    val nodeId: String,
    @SerializedName("organizations_url")
    @Expose
    val organizationsUrl: String,
    @SerializedName("public_gists")
    @Expose
    val publicGists: Int,
    @SerializedName("public_repos")
    @Expose
    val publicRepos: Int,
    @SerializedName("received_events_url")
    @Expose
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    @Expose
    val reposUrl: String,
    @SerializedName("site_admin")
    @Expose
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    @Expose
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    @Expose
    val subscriptionsUrl: String,
    @SerializedName("twitter_username")
    @Expose
    val twitterUsername: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("url")
    @Expose
    val url: String
)