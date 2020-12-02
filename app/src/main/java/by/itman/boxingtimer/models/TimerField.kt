package by.itman.boxingtimer.models

import by.itman.boxingtimer.di.App
import by.itman.boxingtimer.R

enum class TimerField {
    NAME {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.timer_sound_gong_title)
        }
    },
    ROUND_DURATION {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_roundDuration)
        }
    },
    REST_DURATION {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_restDuration)
        }
    },
    ROUND_QUANTITY {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_roundQuantity)
        }
    },
    RUN_UP {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_runUp)
        }
    },
    NOTICE_OF_END_ROUND {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_noticeOfEndRound)
        }
    },
    NOTICE_OF_END_REST {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_noticeOfEndRest)
        }
    },

    SOUND_TYPE_OF_END_ROUND_NOTICE {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_soundType_of_end_round_notice)
        }
    },
    SOUND_TYPE_OF_END_REST_NOTICE {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_soundType_of_end_rest_notice)
        }
    },

    SOUND_TYPE_OF_START_ROUND {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_soundType_of_start_round)
        }
    },
    SOUND_TYPE_OF_START_REST {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_soundType_of_start_rest)
        }
    };

    abstract fun getTitle(): String
}