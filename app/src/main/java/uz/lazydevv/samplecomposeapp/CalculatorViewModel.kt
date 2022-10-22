package uz.lazydevv.samplecomposeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.num)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            CalculatorAction.Calculate -> performCalculation()
            CalculatorAction.Clear -> state = CalculatorState()
            CalculatorAction.Decimal -> enterDecimal()
            CalculatorAction.Delete -> performDeletion()
        }
    }

    private fun performDeletion() {
        when {
            state.num2.isNotBlank() -> state = state.copy(num2 = state.num2.dropLast(1))
            state.operation != null -> state = state.copy(operation = null)
            state.num1.isNotBlank() -> state = state.copy(num1 = state.num1.dropLast(1))
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && state.num1.isNotBlank() && !state.num1.contains(".")) {
            state = state.copy(num1 = state.num1 + ".")
            return
        }

        if (state.num2.isNotBlank() && !state.num2.contains(".")) {
            state = state.copy(num2 = state.num2 + ".")
        }
    }

    private fun performCalculation() {
        val num1 = state.num1.toDoubleOrNull()
        val num2 = state.num2.toDoubleOrNull()

        if (num1 == null || num2 == null) return

        val result = when (state.operation) {
            CalculatorOperation.Add -> num1 + num2
            CalculatorOperation.Divide -> num1 / num2
            CalculatorOperation.Multiply -> num1 * num2
            CalculatorOperation.Subtract -> num1 - num2
            null -> return
        }

        state = state.copy(
            num1 = result.toString().take(15),
            num2 = "",
            operation = null
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.num1.isNotBlank()) state = state.copy(operation = operation)
    }

    private fun enterNumber(num: Int) {
        if (state.operation == null) {
            if (state.num1.length >= MAX_NUM_LENGTH) return

            state = state.copy(num1 = state.num1 + num)
        } else {
            if (state.num2.length >= MAX_NUM_LENGTH) return

            state = state.copy(num2 = state.num2 + num)
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}