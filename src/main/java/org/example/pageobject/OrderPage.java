package org.example.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {

    private WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Список элементов первой формы заказа

    // Заголовок «Для кого самокат»
    private final By forWhomHeader = By.xpath("//div[text()='Для кого самокат']");

    // Поле «Имя»
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");

    // Поле «Фамилия»
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле «Адрес»
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро» (после клика появляется выпадающий список станций)
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");

    // Элемент списка станций метро (текст = название станции)
    private By metroStationOption(String station) {
        return By.xpath("//div[contains(@class,'select-search')]//div[text()='" + station + "']");
    }

    // Поле «Телефон»
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее»
    private final By nextButton = By.xpath("//button[text()='Далее']");

    //Методы
    public void fillForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        // Клик по полю метро открывает список, затем выбираем станцию по тексту
        driver.findElement(metroField).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(metroStationOption(metro)));
        driver.findElement(metroStationOption(metro)).click();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public RentPage clickNextButton() {
        driver.findElement(nextButton).click();
        return new RentPage(driver);
    }
}


