package org.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class BaseTest {
    //Вынос общих настроек в отдельный класс, который унаследуют другие
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Браузер задаётся системным свойством: browser=chrome или browser=firefox
        String browser = System.getProperty("browser", "chrome");
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }


    }

    @AfterEach
    public void tearDown() {
        // закрываем браузер в конце каждого теста
        driver.quit();
    }
}
