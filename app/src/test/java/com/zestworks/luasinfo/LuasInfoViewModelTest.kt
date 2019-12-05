package com.zestworks.luasinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zestworks.luasinfo.common.NetworkResult
import com.zestworks.luasinfo.common.ViewState
import com.zestworks.luasinfo.listing.ListingRepository
import com.zestworks.luasinfo.listing.ListingViewModel
import com.zestworks.luasinfo.listing.Stops
import io.kotlintest.matchers.collections.shouldContainAll
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

class LuasInfoViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val repository = mockk<ListingRepository>()
    private lateinit var viewModel: ListingViewModel


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = ListingViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)

        every {
            runBlocking {
                repository.getLUASForecast(Stops.MAR)
            }
        }.returns(
            NetworkResult.Success(marStopInfo)
        )

        every {
            runBlocking {
                repository.getLUASForecast(Stops.STI)
            }
        }.returns(
            NetworkResult.Success(stiStopInfo)
        )

    }

    @Test
    fun `Test whether return data is correct - BEFORE 12PM`() {
        val calendarBefore12 = Calendar.getInstance()
        calendarBefore12.set(Calendar.HOUR_OF_DAY, 11)
        calendarBefore12.set(Calendar.MINUTE, 59)
        viewModel.onUILoad(calendarBefore12)

        val currentState = viewModel.currentLuasInfo.value
        (currentState is ViewState.Content) shouldBe true
        (currentState as ViewState.Content).viewData.stopName shouldBe Stops.MAR.name

        currentState.viewData.trams shouldContainAll outboundViewList
    }

    @Test
    fun `Test whether return data is correct - AFTER 12PM`() {
        val calendarAfter12 = Calendar.getInstance()
        calendarAfter12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAfter12.set(Calendar.MINUTE, 1)
        viewModel.onUILoad(calendarAfter12)

        val currentState = viewModel.currentLuasInfo.value
        (currentState is ViewState.Content) shouldBe true
        (currentState as ViewState.Content).viewData.stopName shouldBe Stops.STI.name
        currentState.viewData.trams shouldContainAll inboundViewList
    }

    @Test
    fun `Test whether return data is correct - AT 12PM`() {
        val calendarAt12 = Calendar.Builder()
        calendarAt12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAt12.set(Calendar.MINUTE, 0)
        viewModel.onUILoad(calendarAt12.build())

        val currentState = viewModel.currentLuasInfo.value
        (currentState is ViewState.Content) shouldBe true
        (currentState as ViewState.Content).viewData.stopName shouldBe Stops.MAR.name
        currentState.viewData.trams shouldContainAll outboundViewList
    }

    @Test
    fun `Test ERROR response`() {
        val reason = "Something went wrong"
        every {
            runBlocking {
                repository.getLUASForecast(Stops.STI)
            }
        }.returns(
            NetworkResult.Error(reason)
        )

        val calendarAfter12 = Calendar.getInstance()
        calendarAfter12.set(Calendar.HOUR_OF_DAY, 12)
        calendarAfter12.set(Calendar.MINUTE, 1)
        viewModel.onUILoad(calendarAfter12)

        (viewModel.currentLuasInfo.value is ViewState.Error) shouldBe true
        (viewModel.currentLuasInfo.value as ViewState.Error).reason shouldBe reason
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
}


