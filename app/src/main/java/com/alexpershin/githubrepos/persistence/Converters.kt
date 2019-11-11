package com.alexpershin.githubrepos.persistence

import androidx.room.TypeConverter
import com.alexpershin.githubrepos.model.License
import com.alexpershin.githubrepos.model.Owner
import com.alexpershin.githubrepos.model.Permissions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * This class converts from a unknown type into a known type in terms of database types
* */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun getLicenseString(json: String): License? {
        return gson.fromJson<License>(json, License::class.java)
    }

    @TypeConverter
    fun getStringLicense(license: License?): String {
        return gson.toJson(license)
    }

    @TypeConverter
    fun getOwnerString(json: String): Owner? {
        return gson.fromJson<Owner>(json, Owner::class.java)
    }

    @TypeConverter
    fun getStringOwner(owner: Owner?): String {
        return gson.toJson(owner)
    }

    @TypeConverter
    fun getPermissionsString(json: String): Permissions? {
        val listType = object : TypeToken<Permissions>() {
        }.type
        return gson.fromJson<Permissions>(json, listType)
    }

    @TypeConverter
    fun getStringPermissions(permissions: Permissions?): String {
        return gson.toJson(permissions)
    }

}
