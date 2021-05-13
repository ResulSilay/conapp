package com.adapter.multiple

import android.app.Application

class MultipleApp : Application() {

    companion object {

        private val multipleApp: MultipleApp? = null

        fun instance(): MultipleApp? {
            if (multipleApp == null)
                return MultipleApp()

            return null
        }
    }

}