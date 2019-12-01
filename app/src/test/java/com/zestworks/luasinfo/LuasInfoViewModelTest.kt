package com.zestworks.luasinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zestworks.luasinfo.LUASInfoViewModel.State.Error
import com.zestworks.luasinfo.LUASInfoViewModel.State.Success
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LuasInfoViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val repository = mockk<Repository>()
    private lateinit var viewModel: LUASInfoViewModel


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = LUASInfoViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)

        every {
            runBlocking {
                repository.getLUASForecast(LUASInfoViewModel.Stops.MAR)
            }
        }.returns(
            Success(marStopInfo)
        )

        every {
            runBlocking {
                repository.getLUASForecast(LUASInfoViewModel.Stops.STI)
            }
        }.returns(
            Success(stiStopInfo)
        )

    }

    @Test
    fun `Test whether return data is correct - BEFORE 12PM`() {
        val calendarBefore12 = Calendar.getInstance()
        calendarBefore12.set(Calendar.HOUR_OF_DAY, 11)
        calendarBefore12.set(Calendar.SECOND, 59)
        viewModel.onUILoad(calendarBefore12)

        viewModel.currentLuasInfo.value shouldBe Success(marStopInfo)
    }

    @Test
    fun `Test whether return data is correct - AFTER 12PM`() {
        val calendarAfter12 = Calendar.getInstance()
        calendarAfter12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAfter12.set(Calendar.SECOND, 1)
        viewModel.onUILoad(calendarAfter12)

        viewModel.currentLuasInfo.value shouldBe Success(stiStopInfo)
    }

    @Test
    fun `Test whether return data is correct - AT 12PM`() {
        val calendarAt12 = Calendar.Builder()
        calendarAt12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAt12.set(Calendar.SECOND, 0)
        viewModel.onUILoad(calendarAt12.build())

        viewModel.currentLuasInfo.value shouldBe Success(marStopInfo)
    }

    @Test
    fun `Test ERROR response`() {
        val reason = "Something went wrong"
        every {
            runBlocking {
                repository.getLUASForecast(LUASInfoViewModel.Stops.STI)
            }
        }.returns(
            Error(reason)
        )

        val calendarAfter12 = Calendar.getInstance()
        calendarAfter12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAfter12.set(Calendar.SECOND, 1)
        viewModel.onUILoad(calendarAfter12)

        viewModel.currentLuasInfo.value shouldBe Error(reason)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
}


