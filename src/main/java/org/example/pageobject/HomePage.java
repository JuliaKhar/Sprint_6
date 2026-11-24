package org.example.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Список элементов главной страницы, нужных для теста

    // Кнопка принятия куки (появляется при первом входе)
    private By cookiesButton = By.xpath("//button[contains(@class,'App_CookieButton')]");

    // Верхняя кнопка Заказать в хэдере
    private By topOrderButton = By.xpath("//div[contains(@class, 'Header')]//button[text()='Заказать']");


    // Нижняя кнопка Заказать
    private By bottomOrderButton = By.xpath("//button[contains(@class,'Button_Middle') and text()='Заказать']");

    //Заголовок Вопросы о важном
    private By importantQuestionsHeader = By.xpath("//div[text()='Вопросы о важном']");

    // Кнопка-стрелочка раскрывающегося списка (вопрос №1 — индекс 0)
    private By questionButton(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Текст ответа под вопросом (появляется после клика по стрелочке)
    private By answerText(int index) {
        return By.id("accordion__panel-" + index);
    }


    //Методы

    public void open() {
        driver.get("https://qa-scooter.education-services.ru/");
    }

    public void acceptCookies() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(cookiesButton))
                    .click();
        } catch (Exception ignored) {
            // Если кнопки нет — ничего не делаем
        }
    }

    public void clickQuestion(int index) {
        By locator = questionButton(index);
        //т.к.firefox ругается на перекрытие - клик через JS, чтобы обойти перекрывающую картинку (совет ИИ)
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }


    public String getAnswerText(int index) {
        By locator = answerText(index);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    public OrderPage clickTopOrderButton() {
        var button = driver.findElement(topOrderButton);
        button.click();
        return new OrderPage(driver);
    }

    public OrderPage clickBottomOrderButton() {
        var button = driver.findElement(bottomOrderButton);
        button.click();
        return new OrderPage(driver);
    }

}
