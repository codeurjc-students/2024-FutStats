package com.tfg.futstats.selenium.admin.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tfg.futstats.selenium.BaseTest;

public class LeagueFormTest extends BaseTest {

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        scrollToElement(usernameField);
        scrollToElement(passwordField);
        scrollToElement(loginButton);

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.clear();
        passwordField.clear();
        
        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        
        loginButton.click();
    }

    @Test
    public void testLeagueFormFields() {
        testLoginFunctionality(); 

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Crear una liga')]")));
        scrollToElement(createButton);
        assertNotNull(createButton, "El botón 'Crear una liga' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(createButton));
        
        createButton.click();

        WebElement form = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[h2[text()='Nueva Liga']]")));
        scrollToElement(form);
        assertNotNull(form, "El formulario de creación de liga no está presente.");

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement nationalityField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Nacionalidad']")));
        WebElement presidentField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Presidente']")));

        scrollToElement(nameField);
        scrollToElement(nationalityField);
        scrollToElement(presidentField);

        assertNotNull(nameField, "El campo de nombre no se encontró.");
        nameField.clear();
        nameField.sendKeys("Liga de Prueba");
        assertEquals("Liga de Prueba", nameField.getAttribute("value"),
                "El campo de nombre no se ha actualizado correctamente.");

        assertNotNull(nationalityField, "El campo de nacionalidad no se encontró.");
        nationalityField.clear();
        nationalityField.sendKeys("España");
        assertEquals("España", nationalityField.getAttribute("value"),
                "El campo de nacionalidad no se ha actualizado correctamente.");

        assertNotNull(presidentField, "El campo de presidente no se encontró.");
        presidentField.clear();
        presidentField.sendKeys("Juan Pérez");
        assertEquals("Juan Pérez", presidentField.getAttribute("value"),
                "El campo de presidente no se ha actualizado correctamente.");
    }

    @Test
    public void testCreateLeagueButton() {
        testLeagueFormFields();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Crear Liga']")));
        scrollToElement(createButton);
        assertNotNull(createButton, "El botón 'Crear Liga' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(createButton));
        
        createButton.click();
        
        WebDriverWait waitForLeague = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitForLeague.until(ExpectedConditions.urlContains("/leagues/"));

        assertTrue(driver.getCurrentUrl().contains("/leagues/"), "La URL de la liga no es la correcta.");
        System.out.println("Acceso correcto a la liga " + driver.getCurrentUrl());
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLeagueFormFields();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Cancelar']")));
        scrollToElement(cancelButton);
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        
        cancelButton.click();
        
        assertTrue(driver.getCurrentUrl().contains("/leagues"), "La URL de la liga no es la correcta.");
        System.out.println("Acceso correcto a las ligas " + driver.getCurrentUrl());
    }
}
