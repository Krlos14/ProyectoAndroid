package com.example.anitalks.data.source

import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.model.Comment

val sampleAnimeList = listOf(
    Anime(1, "Shingeki no Kyojin", R.string.desc_snk, R.drawable.shingeki, 4.8f, true,
        listOf(Comment("ErenFan", "¡La temporada final es increíble!"))),
    Anime(2, "Jujutsu Kaisen", R.string.desc_jjk, R.drawable.jujutsu, 4.6f, false),
    Anime(3, "Spy x Family", R.string.desc_sxf, R.drawable.spyxfamily, 4.9f, true,
        listOf(Comment("AnyaLover", "Waku waku! Anya is so cute."))),
    Anime(4, "One Piece", R.string.desc_op, R.drawable.one, 4.7f, false)
)