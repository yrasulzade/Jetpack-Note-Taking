package com.example.jetpacknotetaking.util

inline fun <T> T?.ifNotNull(action: (T) -> Unit) {
    if (this != null) {
        action(this)
    }
}

inline fun <T> T?.ifNull(action: () -> Unit) {
    if (this == null) {
        action()
    }
}
