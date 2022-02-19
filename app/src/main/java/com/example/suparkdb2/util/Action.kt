package com.example.suparkdb2.util

enum class Action {
    DECLARE_ENTRANCE,
    DECLARE_LEAVING,
    NO_ACTION
}

fun String?.toAction(): Action {
    return if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)
}