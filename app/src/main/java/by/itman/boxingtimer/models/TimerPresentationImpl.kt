package by.itman.boxingtimer.models

import java.time.Duration

class TimerPresentationImpl(private val timer: TimerModel): TimerPresentation {
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

    override fun getNoticeOfEndRest(): Duration {
        return timer.noticeOfEndRest
    }

    override fun getSoundTypeOfEndRoundNotice(): TimerSoundType {
        return timer.soundTypeOfEndRoundNotice
    }

    override fun getSoundTypeOfEndRestNotice(): TimerSoundType {
        return timer.soundTypeOfEndRestNotice
    }

    override fun getSoundTypeOfStartRound(): TimerSoundType {
        return timer.soundTypeOfStartRound
    }

    override fun getSoundTypeOfStartRest(): TimerSoundType {
        return timer.soundTypeOfStartRest
    }
}