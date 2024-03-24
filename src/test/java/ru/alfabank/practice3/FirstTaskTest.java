package ru.alfabank.practice3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.alfabank.service.Calculator;

public class FirstTaskTest {

    // Напишите тесты для класса Calculator
    // В 4-х тестах используйте @Test

    private final Calculator calculator = new Calculator();

    @Test
    void shouldAddNumbers() {
        Assertions.assertEquals(calculator.add(1, 2), 3);
    }

    @Test
    void shouldSubtractNumbers() {

        Assertions.assertEquals(calculator.subtract(3, 2), 1);
    }

    @Test
    void shouldMultiplyNumbers() {
        Assertions.assertEquals(calculator.multiply(3, 2), 6);
    }

    @Test
    void shouldDivideNumbers() {

        Assertions.assertEquals(calculator.subtract(4, 2), 2);
    }
}
