package ru.alfabank.practice_JUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты счетов")
class BaseTest {

    @BeforeAll
    static void init() {
        System.out.println("Один раз перед всеми тестами");
    }

    @BeforeEach
    void registerUser() {
        System.out.println("Пользователь зарегистрирован");
    }

    @Test
    void  baseTest() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Счет должен создаваться если есть право")
    void anotherBaseTest() {
        Assertions.assertEquals(1,1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"value", "ee", "more", "123"})
    void shouldCheckLetter(String value) {
        Assertions.assertTrue(value.contains("e"));
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldCheckEnum(AccountType value) {
        Assertions.assertNotNull(value);
    }

    @ParameterizedTest
    @CsvSource({
            "debit, 1",
            "credit, 2"})
    void shouldCheckCsv(String accountType, int accountId) {
        Assertions.assertNotNull(accountType);
        Assertions.assertTrue(accountId > 0);
    }

    @AfterAll
    static void close() {
        System.out.println("Один раз после всех тестов");
    }
}
