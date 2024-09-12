package me.newbly.camyomi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
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
)

// id, keb, ke_inf, ke_pri, re, re_nokanji, re_restr, re_inf, re_pri, ant, pos, field, dial, gloss, ex_text, ex_sent