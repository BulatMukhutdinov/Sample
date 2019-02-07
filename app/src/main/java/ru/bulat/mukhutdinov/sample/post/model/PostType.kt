package ru.bulat.mukhutdinov.sample.post.model

enum class PostType(val serializedValue: String) {
    TEXT("text"),
    IMAGE("image"),
    QUOTE("quote"),
    LINK("link"),
    CHAT("chat"),
    AUDIO("audio"),
    VIDEO("video"),
    ANSWER("answer"),
    POSTCARD("postcard"),
    UNKNOWN("unknown")
}