package com.tfg.futstats.selenium;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {

    public WebDriver driver;

	public int port = 8443;
	
	@BeforeEach
	public void setup() {
		options.setAcceptInsecureCerts(true);
    		options.addArguments("--headless=new");
    		options.addArguments("--no-sandbox");
    		options.addArguments("--disable-dev-shm-usage");
    		options.addArguments("--disable-gpu");
	}
	
	@AfterEach
	public void teardown() {
		if(driver != null) {
			driver.quit();
		}
	}
}
