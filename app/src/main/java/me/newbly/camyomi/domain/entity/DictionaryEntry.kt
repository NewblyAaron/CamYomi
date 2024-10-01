package me.newbly.camyomi.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class DictionaryEntry(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "keb") val keb: String?,
    @ColumnInfo(name = "ke_inf") val keInf: String?,
    @ColumnInfo(name = "ke_pri") val kePri: String?,
    @ColumnInfo(name = "re") val re: String?,
    @ColumnInfo(name = "re_nokanji") val reNoKanji: String?,
    @ColumnInfo(name = "re_restr") val reRestr: String?,
    @ColumnInfo(name = "re_inf") val reInf: String?,
    @ColumnInfo(name = "re_pri") val rePri: String?,
    @ColumnInfo(name = "ant") val ant: String?,
    @ColumnInfo(name = "pos") val pos: String?,
    @ColumnInfo(name = "field") val field: String?,
    @ColumnInfo(name = "dial") val dial: String?,
    @ColumnInfo(name = "gloss") val gloss: String?,
    @ColumnInfo(name = "ex_text") val exText: String?,
    @ColumnInfo(name = "ex_sent") val exSent: String?,
) {
    @Ignore var isBookmarked: Boolean = false

    fun getMainKanjiReading(): String {
        if (keb.isNullOrBlank()) return ""

        return keb.split(";")[0]
    }

    fun getMainKanaReading(): String {
        if (re.isNullOrBlank()) return ""

        return re.split(";")[0]
    }

    fun getOtherReadings(): String {
        val kanjiReadings = keb?.split(";") ?: listOf()
        val kanaReadings = re?.split(";") ?: listOf()

        var formattedString = ""

        formattedString += "Other kanji readings: "
        if (kanjiReadings.isEmpty()) {
            formattedString += "None\n"
        } else {
            for (reading in kanjiReadings) {
                formattedString += "$reading, "
            }
            formattedString = formattedString
                .trimEnd()
                .removeSuffix(",")
                .plus("\n")
        }

        formattedString += "Other kana readings: "
        if (kanaReadings.isEmpty()) {
            formattedString += "None\n"
        } else {
            for (reading in kanaReadings) {
                formattedString += "$reading, "
            }
            formattedString =  formattedString
                .trimEnd()
                .removeSuffix(",")
        }

        return formattedString
    }

    fun getGlossary(): String {
        if (gloss.isNullOrBlank()) return ""

        val glossary = gloss.replace("-None", "").split("|")
        var formattedString = ""

        for (def in glossary) {
            formattedString += "• $def\n"
        }

        return formattedString
    }
}

// id, keb, ke_inf, ke_pri, re, re_nokanji, re_restr, re_inf, re_pri, ant, pos, field, dial, gloss, ex_text, ex_sent