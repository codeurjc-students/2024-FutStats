package com.tfg.futstats.selenium;

import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        // Configuramos las opciones de Chrome
        ChromeOptions options = new ChromeOptions();
        // Opciones recomendadas para entornos CI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        // Se espera que la URL del Selenium Grid se pase mediante la variable de entorno SELENIUM_REMOTE_URL
        String seleniumUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (seleniumUrl == null || seleniumUrl.isEmpty()) {
            throw new IllegalStateException("La variable de entorno SELENIUM_REMOTE_URL no está definida");
        }
        
        // Usamos RemoteWebDriver para conectarnos al Selenium Server remoto
        driver = new RemoteWebDriver(new URL(seleniumUrl), options);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
