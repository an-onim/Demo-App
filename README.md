# Demo-App

Приложение-песочница для души.

## TODO-list

* ~~обновить версии~~
* разбить на модули
* ~~convention plugin~~
* ~~сериализация в core:network~~
* core:network - оформить
* оформить Ридми

После:
* в GalleryBlock если картинка не загружается, нужно сделать placeholder
* switch - добавить

## Структура

```text
                   :domain
                  ▲       ▲
                 /         \
                /           \
      :presentation        :data
           ▲   │              │
           │   │              ▼
           │   └────> :core:navigation
           │
           │
         :app ─────────────> :data
           │
           └───────────────> :core:navigation

:data ─────────────────────> :core:network

:applib ───────────────────> :presentation
```

:presentation
UI + состояние экрана

:domain
модели предметной области + контракты получения данных

:data
конкретная работа с Retrofit/Firebase + реализация контрактов

:core:network
настройка Retrofit/OkHttp/Firebase