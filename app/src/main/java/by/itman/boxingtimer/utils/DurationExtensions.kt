package by.itman.boxingtimer.utils

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */

import java.time.Duration

/**
 * Total seconds (rounded to nearest second)
*/
fun Duration.getRoundedSeconds(): Long {
    return (this.toMillis() + 500L) / 1000L
}
/**
*  Seconds remainder after minutes
*/
fun Duration.toSeconds(): Long {
    return this.seconds - this.toMinutes() * 60
}

/** Round to nearest multiplier of millis
 *  Duration.ofMillis(1900).roundTo(1000)  ->  2000 ms
 * Duration.ofMillis(2000).roundTo(1000)  ->  2000 ms
 * Duration.ofMillis(2100).roundTo(1000)  ->  2000 ms
 */
fun Duration.roundTo(millis: Long): Duration {
    return Duration.ofMillis((this.toMillis() + millis / 2) / millis * millis)
}

/**
 * Formats to min:sec (rounded to the nearest second, 2 decimals for each value)
 */
fun Duration.timerFormat(): String {
    val rounded = this.roundTo(1000L); // round to the nearest second
    return String.format("%02d:%02d", rounded.toMinutes(), rounded.toSeconds())
}