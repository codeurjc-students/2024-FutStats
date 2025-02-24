package com.tfg.futstats.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
public void setUp() throws Exception {
    ChromeOptions options = new ChromeOptions();
    Path tempProfileDir = Files.createTempDirectory("chromeProfile");
    options.addArguments("--user-data-dir=" + tempProfileDir.toAbsolutePath().toString());
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
}

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        
    }
}
