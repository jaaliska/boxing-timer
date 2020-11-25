package by.itman.boxingtimer.presenters

import by.itman.boxingtimer.App
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
    SOUND_TYPE {
        override fun getTitle(): String {
            return App.applicationContext().resources.getString(R.string.txt_title_name_soundType)
        }
    };

    abstract fun getTitle(): String
}