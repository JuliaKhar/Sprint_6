package org.example.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RentPage {

    private WebDriver driver;

    public RentPage(WebDriver driver) {
        this.driver = driver;
    }

    //Список элементов второй формы заказа

    // Заголовок «Про аренду»
    private final By rentHeader = By.xpath("//div[text()='Про аренду']");

    // Поле даты доставки
    private final By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // Выпадающий список «Срок аренды»
    private final By rentalPeriodField = By.xpath("//div[contains(@class,'Dropdown-control')]");

    // Вариант срока аренды в списке
    private By rentalPeriodOption(String option) {
        return By.xpath("//div[contains(@class,'Dropdown')]//div[text()='" + option + "']");
    }

    // Чекбокс цвета самоката
    private By colorCheckbox(String color) {
        return By.xpath("//label[contains(@for,'" + color + "')]");
    }

    // Поле «Комментарий для курьера»
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать» внизу формы
    private final By orderButton = By.xpath("//button[contains(@class,'Button_Middle') and text()='Заказать']");

    // Кнопка «Да» в окне подтверждения «Хотите оформить заказ?»
    private final By yesButton = By.xpath("//button[text()='Да']");

    // Окно успешного заказа («Заказ оформлен»)
    private final By successOrder = By.xpath("//div[contains(@class,'Order_ModalHeader') and text()='Заказ оформлен']");

    // Локатор используется, чтобы закрыть календарь кликом вне него, для избежания ошибок перекрытия в firefox
    private final By bodyElement = By.tagName("body");

    //Методы
    public void fillRentDetails(String date, String period, boolean black, boolean grey, String comment) {
        driver.findElement(deliveryDateField).sendKeys(date);
// т.к.firefox ругается на перекрытие, кликаем в любое место вне календаря
        new Actions(driver).moveToElement(driver.findElement(bodyElement)).click().perform();
        driver.findElement(rentalPeriodField).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodOption(period)));
        driver.findElement(rentalPeriodOption(period)).click();
        if (black) driver.findElement(colorCheckbox("black")).click();
        if (grey) driver.findElement(colorCheckbox("grey")).click();
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
        // После клика появляется окно подтверждения — жмём «Да»
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(yesButton));
        driver.findElement(yesButton).click();
    }

    public boolean isSuccessOrderDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(successOrder))
                .isDisplayed();
    }
}



