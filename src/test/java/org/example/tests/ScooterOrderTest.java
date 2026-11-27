package org.example.tests;

import org.example.pageobject.HomePage;
import org.example.pageobject.OrderPage;
import org.example.pageobject.RentPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScooterOrderTest extends BaseTest {

    //Данные для позитивного сценария для верхней и нижней кнопки
    private static Stream<Arguments> orderData() {
        return Stream.of(
                // Сценарий 1: заказ через верхнюю кнопку
                Arguments.of("top", "Ника", "Смирнова", "ул. Мира, 5", "Черкизовская",
                        "89051234567", "сутки", true, false, "Позвонить заранее"),
                // Сценарий 2: заказ через нижнюю кнопку
                Arguments.of("bottom", "Иван", "Петров", "ул. Труда", "Комсомольская",
                        "89109998877", "двое суток", false, true, "Тест")
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    void positiveOrderFlowSucceeds(String buttonPoint, String name, String surname, String address,
                                   String metro, String phone, String period,
                                   boolean black, boolean grey, String comment) {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.acceptCookies();

        OrderPage orderPage;
        if ("top".equals(buttonPoint)) {
            orderPage = homePage.clickTopOrderButton();
        } else if ("bottom".equals(buttonPoint)) {
            orderPage = homePage.clickBottomOrderButton();
        } else {
            throw new IllegalArgumentException("Неизвестный buttonPoint: " + buttonPoint);
        }
        orderPage.fillForm(name, surname, address, metro, phone);

        RentPage rentDetailsPage = orderPage.clickNextButton();
        String tomorrow = LocalDate.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        rentDetailsPage.fillRentDetails(tomorrow, period, black, grey, comment);
        rentDetailsPage.clickOrderButton();
        rentDetailsPage.confirmOrder();

        assertTrue(rentDetailsPage.isSuccessOrderDisplayed(),
                "Появляется всплывающее окно об успешном создании заказа");
    }

}
