package com.tfg.futstats.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
public void setUp() throws Exception {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
}

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        
    }
}
