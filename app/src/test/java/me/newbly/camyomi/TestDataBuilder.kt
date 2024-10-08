package me.newbly.camyomi

import com.atilika.kuromoji.TokenizerBase
import com.atilika.kuromoji.ipadic.Tokenizer
import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.domain.entity.Word

class TestDataBuilder {
    data class JapaneseText(
        private val text: String
    ) {
        private val words: List<Word>

        init {
            words = tokenizeText(text)
        }

        fun getSentence(): String = text
        fun getWords(): List<Word> = words

        override fun toString(): String = text

        private fun tokenizeText(text: String): List<Word> {
            val tokenizer = Tokenizer.Builder().mode(TokenizerBase.Mode.SEARCH).build()
            val tokens = tokenizer.tokenize(text)
            val words = mutableListOf<Word>()
            tokens.forEach {
                words.add(Word(
                    originalForm = it.surface,
                    baseForm = it.baseForm
                ))
            }

            return words
        }
    }

    companion object {
        fun buildJapaneseText(text: String = SAMPLE_SENTENCE): JapaneseText = JapaneseText(text)
        fun buildDefinition(): DictionaryEntry = SAMPLE_DEFINITION
        fun buildBookmark(): Bookmark = SAMPLE_BOOKMARK
        fun buildRecentScan(): RecentScan = SAMPLE_RECENT_SCAN

        private const val SAMPLE_SENTENCE = "今日は暑いですね"

        private val SAMPLE_DEFINITION =
            DictionaryEntry(
                id = 1579110,
                keb = "今日",
                keInf = "",
                kePri = "ichi1|news1|nf02",
                re = "きょう;こんにち;こんち;こんじつ",
                reNoKanji = "F;F;F;F",
                reRestr = ";;;",
                reInf = "gikun (meaning as reading) or jukujikun (special kanji reading);;;",
                rePri = "ichi1;ichi1|news1|nf02;;",
                ant = ";",
                pos = "noun (common) (futsuumeishi)|adverb (fukushi);noun (common) (futsuumeishi)|adverb (fukushi), field=;, dial=;",
                gloss = "today-None|this day-None;these days-None|recently-None|nowadays-None",
                exText = "今日|今日;今日",
                exSent = "その事故は去年の今日、起きたのだ。/The accident happened a year ago today.|今日は何をしたいですか。/What would you like to do today?;今日ますます多くの人が、都会より田舎の生活を好むようになっています。/Nowadays more and more people prefer country life to city life.",
                field = null,
                dial = null
            )

        private val SAMPLE_BOOKMARK =
            Bookmark(entryId = 1579110)

        private val SAMPLE_RECENT_SCAN =
            RecentScan(
                text = "今日は暑いですね", // kyou wa atsui desu ne (today is hot, isn't it?)
            )
    }
}