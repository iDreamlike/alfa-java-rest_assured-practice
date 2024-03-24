package ru.alfabank.practice3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.alfabank.service.Calculator;

import java.util.stream.Stream;

public class SecondTaskTest {

    // Напишите параметризованные тесты для класса Calculator.
    // Используйте данные из csv и method источников.

    private final Calculator calculator = new Calculator();

    // =================================================== СЛОЖЕНИЕ ====================================================
    @ParameterizedTest
    @CsvSource({
            "1, 2, 3"
    })
    void shouldAddNumbersByCsvSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 + num2, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvFileSubtract.csv")
    void shouldAddNumbersByCsvFileSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 - num2, result);
    }

    @ParameterizedTest
    @MethodSource("methodSourceAdd")
    void shouldAddNumbersByMethodSource(double num1, double num2, double result) {
        Assertions.assertEquals(calculator.add(num1, num2), result);
    }

    static Stream<Arguments> methodSourceAdd() {
        return Stream.of(
                Arguments.of(10, 20, 30)
        );
    }

    // =================================================== ВЫЧИТАНИЕ ===================================================
    @ParameterizedTest
    @CsvSource({
            "4, 2, 2"
    })
    void shouldSubtractNumbersByCsvSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 - num2, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvFileSubtract.csv")
    void shouldSubtractNumbersByCsvFileSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 - num2, result);
    }

    @ParameterizedTest
    @MethodSource("methodSourceSubtract")
    void shouldSubtractNumbersByMethodSource(double num1, double num2, double result) {
        Assertions.assertEquals(calculator.subtract(num1, num2), result);
    }

    static Stream<Arguments> methodSourceSubtract() {
        return Stream.of(
                Arguments.of(5, 1, 4)
        );
    }

    // =================================================== УМНОЖЕНИЕ ===================================================
    @ParameterizedTest
    @CsvSource({
            "4, 2, 8"
    })
    void shouldMultiplyNumbersByCsvSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 * num2, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvFileMultiply.csv")
    void shouldMultiplyNumbersByCsvFileSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 * num2, result);
    }

    @ParameterizedTest
    @MethodSource("methodSourceMultiply")
    void shouldMultiplyNumbersByMethodSource(double num1, double num2, double result) {
        Assertions.assertEquals(calculator.multiply(num1, num2), result);
    }

    static Stream<Arguments> methodSourceMultiply() {
        return Stream.of(
                Arguments.of(5, 1, 5)
        );
    }
    // =================================================== ДЕЛЕНИЕ =====================================================
    @ParameterizedTest
    @CsvSource({
            "8, 4, 2"
    })
    void shouldDivideNumbersByCsvSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 / num2, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvFileDivide.csv")
    void shouldDivideNumbersByCsvFileSource(double num1, double num2, double result) {
        Assertions.assertEquals(num1 / num2, result);
    }

    @ParameterizedTest
    @MethodSource("methodSourceDivide")
    void shouldDivideNumbersByMethodSource(double num1, double num2, double result) {
        Assertions.assertEquals(calculator.divide(num1, num2), result);

    }

    static Stream<Arguments> methodSourceDivide() {
        return Stream.of(
                Arguments.of(15, 3, 5)
        );
    }
}
