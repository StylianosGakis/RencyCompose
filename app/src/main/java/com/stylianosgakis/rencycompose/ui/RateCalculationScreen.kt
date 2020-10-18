package com.stylianosgakis.rencycompose.ui

import android.icu.math.BigDecimal
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.stylianosgakis.rencycompose.extensions.times
import com.stylianosgakis.rencycompose.model.Rate
import com.stylianosgakis.rencycompose.rates.RateCalculationViewModel
import com.stylianosgakis.rencycompose.util.formatToBigDecimal
import com.stylianosgakis.rencycompose.util.formatToThreeDecimalPlaces
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RatesScreen(
    viewModel: RateCalculationViewModel,
) {
    val viewState = viewModel.viewState.collectAsState()
    val rateList = remember(viewState.value.rateList) { viewState.value.rateList }
    val selectedRate = remember(viewState.value.currentSelectedRate) {
        viewState.value.currentSelectedRate
    }

    LaunchedTask {
        viewModel.startUpdates()
    }

    LazyColumnFor(rateList) { rate ->
        val isFirstItem = rate == rateList.first()
        val calculatedRate = remember(selectedRate, rate) {
            rate.rate * selectedRate.rate
        }

        val currencyItemModifier = if (isFirstItem) {
            Modifier.preferredHeight(100.dp)
        } else {
            Modifier.preferredHeight(100.dp)
                .clickable { viewModel.setQueryCurrencyType(rate.currency) }
        }

        CurrencyDetailsItem(
            modifier = currencyItemModifier,
            selectedRate = selectedRate,
            thisRate = rate,
            endComposable = if (isFirstItem) {
                {
                    ClickableRateLabel(
                        selectedRate = selectedRate,
                        onNewRateSelected = { newRate: BigDecimal -> viewModel.setRate(newRate) }
                    )
                }
            } else {
                {
                    SimpleRateLabel(text = "${calculatedRate.formatToThreeDecimalPlaces()} ${rate.currency}")
                }
            }
        )
        if (rate != rateList.last()) {
            Divider(
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                thickness = 1.dp,
            )
        }
    }
}

@Composable
fun CurrencyDetailsItem(
    modifier: Modifier = Modifier,
    selectedRate: Rate,
    thisRate: Rate,
    endComposable: @Composable () -> Unit,
) {
    Box(
        modifier.padding(horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountryFlag(
                Modifier.padding(vertical = 16.dp),
                thisRate
            )
            Spacer(modifier = Modifier.preferredWidth(12.dp))
            Column(
                Modifier.weight(1f, fill = true),
            ) {
                Text(thisRate.currency.displayName)
                if (selectedRate.currency != thisRate.currency) {
                    ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                        Text("${selectedRate.currency} 1 = ${thisRate.currency} ${thisRate.rate.formatToThreeDecimalPlaces()}")
                    }
                }
            }
            endComposable()
        }
    }
}

@Composable
private fun CountryFlag(
    modifier: Modifier = Modifier,
    rate: Rate,
) {
    Card(
        modifier.aspectRatio(1.0f),
        shape = RoundedCornerShape(16.dp)
    ) {
        SimpleCoilImage(
            Modifier.fillMaxSize(),
            url = rate.flagUrl
        )
    }
}

@Composable
fun ClickableRateLabel(
    selectedRate: Rate,
    onNewRateSelected: (BigDecimal) -> Unit,
) {
    val (showEditDialog, setShowEditDialog) = remember { mutableStateOf(false) }
    val showDialog = { setShowEditDialog(true) }
    val dismissDialog = { setShowEditDialog(false) }

    Button(
        onClick = showDialog
    ) {
        SimpleRateLabel(text = "${selectedRate.rate.formatToThreeDecimalPlaces()} ${selectedRate.currency}")
    }

    if (showEditDialog) {
        SelectRateDialog(
            initialText = selectedRate.rate.formatToThreeDecimalPlaces(),
            dismissDialog = dismissDialog,
            onNewRateSelected = onNewRateSelected,
        )
    }
}

@Composable
fun SelectRateDialog(
    initialText: String,
    dismissDialog: () -> Unit,
    onNewRateSelected: (BigDecimal) -> Unit,
) {
    val (rateText, setRateText) = remember { mutableStateOf(initialText) }
    val isValidRateText = remember(rateText) {
        rateText.formatToBigDecimal() != BigDecimal.ZERO
    }

    Dialog(
        onDismissRequest = dismissDialog,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
            contentColor = contentColorFor(MaterialTheme.colors.surface)
        ) {
            Column(
                Modifier.padding(16.dp),
            ) {
                OutlinedTextField(
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = rateText,
                    onValueChange = { setRateText(it) },
                    isErrorValue = isValidRateText.not()
                )
                Spacer(modifier = Modifier.preferredHeight(16.dp))
                Row(
                    Modifier.align(Alignment.End)
                ) {
                    Button(
                        onClick = { dismissDialog() }
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.preferredWidth(16.dp))
                    Button(
                        enabled = isValidRateText,
                        onClick = {
                            onNewRateSelected(rateText.formatToBigDecimal())
                            dismissDialog()
                        }
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleRateLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text
    )
}