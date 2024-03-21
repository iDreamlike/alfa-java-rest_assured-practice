package ru.alfabank.practice3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.alfabank.service.Calculator;

public class SecondTaskTest {

    // Напишите параметризованные тесты для класса Calculator.
    // Используйте данные из csv и method источников.

    Calculator calculator = new Calculator();

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3"
    })
    void shouldAddNumbers(double first, double second, double third) {
        Assertions.assertEquals(first + second, third);
    }
//
//    @ParameterizedTest
//    @MethodSource("calculator.add")
//    void testAdditionWithMethodSource(int num1, int num2, int expectedResult) {
//        double result = calculator.add(num1, num2);
//        Assertions.assertEquals(expectedResult, result);
//    }
//

}
