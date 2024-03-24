package ru.alfabank.practice3;

import org.junit.jupiter.api.*;
import ru.alfabank.annotations.AccountServiceTest;
import ru.alfabank.service.AccountService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AccountServiceTest
public class ThirdTaskTest {

    // Создайте три теста для класса AccountService
    // Перед каждым тестом создавайте экземпляр AccountService, а после каждого удаляйте.
    // Задайте порядок тестов(3, 1, 2)
    // Укажите 2 теста с тегом Smoke и запустите только их.
    // Создайте мета-аннотацию AccountServiceTest, которая будет включать в себя только тесты из этого класса

    AccountService accountService;

    @BeforeEach
    void init() {
        accountService = new AccountService();
    }

    @Test
    @Order(3)
    @Tag("Smoke")
    void simpleTest1() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(1)
    @Tag("Smoke")
    void simpleTest2() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(2)
    void simpleTest3() {
        Assertions.assertTrue(true);
    }

    @AfterEach
    void delete() {
        accountService = null;
    }
}
