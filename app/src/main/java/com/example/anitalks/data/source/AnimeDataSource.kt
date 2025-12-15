package com.example.anitalks.data.source

import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.model.Comment


val sampleAnimeList = listOf(
    Anime(
        1,
        "Shingeki no Kyojin",
        "La humanidad vive protegida por tres muros de las gigantescas criaturas humanoides devoradoras de hombres llamadas Titanes. La historia se centra en Eren Jaeger, su hermana Mikasa Ackerman y su amigo Armin Arlert, cuyas vidas cambian drásticamente tras la aparición de un Titán Colosal.",
        4.8f,
        listOf("Acción", "Fantasía Oscura", "Militar"),
        imageResId = R.drawable.shingeki,
        isFavorite = true,
        comments = listOf(
            Comment(1, "ErenFan", "¡La temporada final es increíblemente intensa!", System.currentTimeMillis() - 3600000)
        )
    ),
    Anime(
        2,
        "Jujutsu Kaisen",
        "Yuji Itadori es un estudiante de secundaria con habilidades físicas extraordinarias. Su vida da un giro cuando, para salvar a sus amigos, se traga un objeto maldito y se une a una organización secreta de Hechiceros Jujutsu para luchar contra las maldiciones.",
        4.6f,
        listOf("Acción", "Sobrenatural", "Escuela"),
        imageResId = R.drawable.jujutsu,
    ),
    Anime(
        3,
        "Spy x Family",
        "Para mantener la paz mundial, un espía llamado 'Twilight' debe formar una 'familia' falsa. Pero su esposa es una asesina letal y su hija adoptiva es una telépata, ¡y nadie conoce la verdadera identidad del otro!",
        4.9f,
        listOf("Comedia", "Espías", "Slice of Life"),
        imageResId = R.drawable.spyxfamily,
        isFavorite = true,
        comments = listOf(
            Comment(1, "AnyaLover", "Anya es lo más lindo y gracioso 💖 Waku waku!", System.currentTimeMillis()),
            Comment(2, "TwilightFan", "Gran mezcla de acción y humor.", System.currentTimeMillis() - 7200000)
        )
    ),
    Anime(
        4,
        "One Piece",
        "Monkey D. Luffy, un joven cuyo cuerpo adquirió propiedades de goma tras comer accidentalmente una Fruta del Diablo, viaja con su tripulación, los Piratas del Sombrero de Paja, en busca del tesoro definitivo conocido como el One Piece.",
        4.7f,
        listOf("Aventura", "Shonen", "Fantasía"),
        imageResId = R.drawable.one,
    )
)
