package by.itman.boxingtimer.providers

import android.content.SharedPreferences
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.spy
import java.time.Duration
import kotlin.random.Random

class PrefsTimerProviderTests {

    private lateinit var provider: PrefsTimerProvider
    private lateinit var prefsMock: SharedPreferences

    @Before
    fun setup() {
        prefsMock = SPMockBuilder().createSharedPreferences()
        provider = PrefsTimerProvider(prefsMock)
    }

    @Test
    fun testSaveNew() {
        val timer = createTimer(null)
        val timerCopy = timer.copy()
        val savedTimer = provider.save(timer)
        // passed object shouldn't have been changed
        assertEquals(timer, timerCopy)
        // id has been created
        assertNotNull(savedTimer.id)
        assertTimersDataAreEqual(timer, savedTimer)

        val timer2 = createTimer(null)
        val savedTimer2 = provider.save(timer2)
        // unique id has been generated
        assertNotSame(savedTimer.id, savedTimer2.id)
        assertTimersDataAreEqual(timer2, savedTimer2)
        assertTrue(false)
    }

    @Test
    fun testSaveExisting() {
        val timer = createTimer(null)
        var savedTimer = provider.save(timer)
        val updatedTimer = createTimer(savedTimer.id)
        savedTimer = provider.save(updatedTimer)
        assertTimersAreEqual(savedTimer, updatedTimer)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testSaveFail() {
        provider.save(createTimer(42))
    }

    @Test
    fun testGetById() {
        val savedTimer = provider.save(createTimer(null))
        var retrievedTimer = provider.getById(savedTimer.id!!)
        assertNotNull(retrievedTimer)
        assertNotSame(savedTimer, retrievedTimer)
        assertTimersAreEqual(savedTimer, retrievedTimer!!)

        // check timer object isn't internally tracked
        val oldName = savedTimer.name
        savedTimer.name = "new name"
        retrievedTimer = provider.getById(savedTimer.id!!)
        assertEquals(retrievedTimer!!.name, oldName)
    }

    @Test
    fun testGetByIdNotExisting() {
        val notExistingTimer = provider.getById(42)
        assertNull(notExistingTimer)

        val savedTimer = provider.save(createTimer(null))

        prefsMock.edit().clear().apply()
        val retrievedTimer = provider.getById(savedTimer.id!!)
        assertNull(retrievedTimer)
    }

    @Test
    fun testGetAll() {
        val timers = Array(5) { createTimer(null) }

        var retrievedTimers = provider.getAll()
        assertEquals(retrievedTimers.size, 0)

        val savedTimers = timers.map { provider.save(it) }
        retrievedTimers = provider.getAll()

        assertEquals(timers.size, retrievedTimers.size)
        // check the order is preserved
        for (i in savedTimers.indices) {
            assertTimersAreEqual(savedTimers[i], retrievedTimers[i])
        }
    }

    @Test
    fun testRemove() {
        val timers = Array(2) { createTimer(null) }
        val savedTimers = timers.map { provider.save(it) }

        provider.remove(savedTimers[0])
        val removedTimer = provider.getById(savedTimers[0].id!!)
        assertNull(removedTimer)

        val retrievedTimers = provider.getAll()
        assertEquals(retrievedTimers.size, 1)
        assertTimersAreEqual(retrievedTimers[0], savedTimers[1])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRemoveNotExisting() {
        val existingTimer = provider.save(createTimer(null))
        val timerWithNotExistingId = createTimer(existingTimer.id!! + 1)
        provider.remove(timerWithNotExistingId)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRemoveWithNullId() {
        provider.remove(createTimer(null))
    }

    @Test
    fun testIsTransactional() {
        val prefsSpy = spy(prefsMock)
        val prefsEditor = prefsMock.edit()
        val editorSpy = spy(prefsEditor)

        val provider = PrefsTimerProvider(prefsSpy)
        val timer = provider.save(createTimer(null))

        Mockito.`when`(prefsSpy.edit()).thenReturn(editorSpy)

        Mockito.`when`(editorSpy.putString(Matchers.anyString(), Matchers.anyString()))
            .thenThrow(OutOfMemoryError())

        try {
            provider.remove(timer)
        }
        catch (e: Throwable) {
            val retrievedTimers = provider.getAll()
            // provider still has 1 timer
            assertEquals(retrievedTimers.size, 1)

            // check timer is not corrupted
            val retrievedTimer = provider.getById(timer.id!!)
            assertNotNull(retrievedTimer)
            assertTimersAreEqual(timer, retrievedTimer!!)
            return
        }

        fail("Exception have not been thrown")
    }

    private fun createTimer(id: Int?): TimerModel {
        return TimerModel(
            id,
            java.util.UUID.randomUUID().toString(),
            Duration.ofMinutes(Random.nextLong(1, 10)),
            Duration.ofMinutes(Random.nextLong(1, 10)),
            Random.nextInt(),
            Duration.ofMinutes(Random.nextLong(1, 10)),
            Duration.ofMinutes(Random.nextLong(1, 10)),
            getRandomTimerSoundType()
        )
    }

    private fun getRandomTimerSoundType(): TimerSoundType {
        val values = TimerSoundType.values()
        return values[Random.nextInt(values.size)]
    }

    private fun assertTimersAreEqual(expected: TimerModel, actual: TimerModel) {
        assertEquals(expected.id, actual.id)
        assertTimersDataAreEqual(expected, actual)
    }

    /**
     * Compare data fields except id
     */
    private fun assertTimersDataAreEqual(expected: TimerModel, actual: TimerModel) {
        assertEquals(expected.name, actual.name)
        assertEquals(expected.roundDuration, actual.roundDuration)
        assertEquals(expected.restDuration, actual.restDuration)
        assertEquals(expected.roundQuantity, actual.roundQuantity)
        assertEquals(expected.runUp, actual.runUp)
        assertEquals(expected.noticeOfEndRound, actual.noticeOfEndRound)
        assertEquals(expected.soundType, actual.soundType)
    }
}