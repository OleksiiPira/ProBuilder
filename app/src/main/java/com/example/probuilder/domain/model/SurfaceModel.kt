package com.example.probuilder.domain.model

import com.example.probuilder.domain.enums.DisplayableEnum

enum class SurfaceType(override val label: String): DisplayableEnum {
    WALL("стіна"),
    CEILING("стеля"),
    FLOOR("підлога"),
    OTHER("інше");
}
enum class SurfaceOption(override val label: String): DisplayableEnum {
    HEIGHT("Висота"), // WALL, OTHER
    WIDTH("Ширина"), // WALL, CEILING, FLOOR, OTHER
    LENGTH("Довжина"), // CEILING, FLOOR
    DEPTH("Глибина"), // OTHER
    AREA("Площа"),
    PERIMETER("Периметр"),
}
