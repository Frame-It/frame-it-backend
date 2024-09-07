package com.org.framelt.project.domain

enum class Spot {
    SEOUL,
    BUSAN,
    DAEGU,
    INCHEON,
    GWANGJU,
    DAEJEON,
    ULSAN,
    GYEONGGI,
    GANGWON,
    CHUNGCHEONG,
    JEOLLA,
    GYEONGSANG,
    JEJU,
    ;

    companion object {
        fun of(spot: String): Spot = valueOf(spot.uppercase())
    }
}
