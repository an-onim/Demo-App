package ru.social.core.base

interface BaseEvent

interface BaseViewState

interface EventHandler<T: BaseEvent> {
    fun handle(event: T)
}