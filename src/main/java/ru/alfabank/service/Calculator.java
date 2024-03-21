package ru.alfabank.service;

public class Calculator {

    // Метод для сложения двух чисел
    public double add(double number1, double number2) {
        return number1 + number2;
    }

    // Метод для вычитания двух чисел
    public double subtract(double number1, double number2) {
        return number1 - number2;
    }

    // Метод для умножения двух чисел
    public double multiply(double number1, double number2) {
        return number1 * number2;
    }

    // Метод для деления двух чисел
    public double divide(double number1, double number2) {
        if (number2 == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return number1 / number2;
    }
}
