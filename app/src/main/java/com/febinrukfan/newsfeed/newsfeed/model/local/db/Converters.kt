package com.febinrukfan.newsfeed.newsfeed.model.local.db

import androidx.room.TypeConverter
import com.febinrukfan.newsfeed.newsfeed.model.TypeAttributes

class Converters {

    @TypeConverter
    fun fromTypeAttributes(typeAttributes: TypeAttributes) : String{
        return typeAttributes.imageLarge
    }

    @TypeConverter
    fun toTypeAttributes(imageLarge: String): TypeAttributes {
        return TypeAttributes(imageLarge)
    }
}