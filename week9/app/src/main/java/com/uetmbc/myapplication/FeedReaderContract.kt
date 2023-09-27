package com.uetmbc.myapplication

import android.provider.BaseColumns


class FeedReaderContract

private constructor() {
    /* Inner class that defines the table contents */
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
}
