package com.andi.githubuserapplication.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="githubApp")
class UserEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "avatar_url")
    val avatar_url:String,

    @field:ColumnInfo(name = "login")
    val login:String,

    @field:ColumnInfo(name = "type")
    val type:String,

    @field:ColumnInfo(name = "favorite")
    var favorite:Boolean=false
)