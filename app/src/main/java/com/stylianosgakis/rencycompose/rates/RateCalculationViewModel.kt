package com.stylianosgakis.rencycompose.rates

import android.icu.math.BigDecimal
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylianosgakis.rencycompose.model.Currency
import com.stylianosgakis.rencycompose.model.Rate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RateCalculationViewModel @ViewModelInject constructor(
    private val fetchRatesUseCase: FetchRatesUseCase,
    @Assisted savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        val DEFAULT_RATE = Rate(Currency.EUR, BigDecimal.ONE)
        val INITIAL_VIEW_STATE =
            ViewState(rateList = listOf(DEFAULT_RATE), currentSelectedRate = DEFAULT_RATE)
    }

    private val _selectedRate = MutableStateFlow(DEFAULT_RATE)
    private val _stateList = MutableStateFlow(listOf(DEFAULT_RATE))
    private val _viewState =
        MutableStateFlow(INITIAL_VIEW_STATE)
    val viewState: StateFlow<ViewState> get() = _viewState

    init {
        combine(_selectedRate, _stateList) { selectedRate, rateList ->
            ViewState(selectedRate, rateList)
        }
            .onEach { _viewState.value = it }
            .launchIn(viewModelScope)

        _selectedRate
            .onEach { startUpdates() }
            .launchIn(viewModelScope)
    }

    fun setQueryCurrencyType(queryCurrencyType: Currency) {
        _selectedRate.value = _selectedRate.value.copy(currency = queryCurrencyType)
    }

    fun setRate(rate: BigDecimal) {
        _selectedRate.value = _selectedRate.value.copy(rate = rate)
    }

    private lateinit var fetchingJob: Job

    fun startUpdates() {
        if (this::fetchingJob.isInitialized) {
            fetchingJob.cancel()
        }
        fetchingJob = fetchRatesUseCase(_selectedRate.value.currency)
            .onEach { _stateList.value = it }
            .launchIn(viewModelScope)
    }
}

data class ViewState(
    val currentSelectedRate: Rate,
    val rateList: List<Rate>,
)