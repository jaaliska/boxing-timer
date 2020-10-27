package by.itman.boxingtimer.presenters

import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.views.TimerPresentation
import java.time.Duration

class TimerPresentationImpl(private val timer: TimerModel): TimerPresentation  {
    override fun getName(): String {
        return timer.name
    }

    override fun getRoundDuration(): Duration {
        return timer.roundDuration
    }

    override fun getRestDuration(): Duration {
        return timer.restDuration
    }

    override fun getRoundQuantity(): Int {
        return timer.roundQuantity
    }

    override fun getRunUp(): Duration {
        return timer.runUp
    }

    override fun getNoticeOfEndRound(): Duration {
        return timer.noticeOfEndRound
    }

    override fun getSoundType(): TimerSoundType {
        return timer.soundType
    }

}