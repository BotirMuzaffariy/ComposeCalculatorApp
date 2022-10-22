package uz.lazydevv.samplecomposeapp

sealed class CalculatorAction {

    data class Number(val num: Int) : CalculatorAction()

    data class Operation(val operation: CalculatorOperation) : CalculatorAction()

    object Clear : CalculatorAction()

    object Delete : CalculatorAction()

    object Decimal : CalculatorAction()

    object Calculate : CalculatorAction()
}
