package com.mjanotta.databasecomparison

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class ZonedDateTimeDeserializer : JsonDeserializer<ZonedDateTime> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ZonedDateTime {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(json.asString, ZonedDateTime::from)
    }
}