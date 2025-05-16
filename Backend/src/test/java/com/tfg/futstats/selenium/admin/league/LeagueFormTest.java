package com.tfg.futstats.selenium.admin.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.tfg.futstats.selenium.BaseTest;

public class LeagueFormTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testLeagueFormFields() {
        testLoginFunctionality(); 

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Crear una liga')]")));

        WebElement createButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Crear una liga')]")));

        createButton.click();

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement form = wait1
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[h2[text()='Nueva Liga']]")));
        assertNotNull(form, "El formulario de creación de liga no está presente.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        assertNotNull(nameField, "El campo de nombre no se encontró.");
        nameField.sendKeys("Liga de Prueba");
        assertEquals("Liga de Prueba", nameField.getAttribute("value"),
                "El campo de nombre no se ha actualizado correctamente.");

        WebElement nationalityField = driver.findElement(By.xpath("//input[@placeholder='Nacionalidad']"));
        assertNotNull(nationalityField, "El campo de nacionalidad no se encontró.");
        nationalityField.sendKeys("España");
        assertEquals("España", nationalityField.getAttribute("value"),
                "El campo de nacionalidad no se ha actualizado correctamente.");

        WebElement presidentField = driver.findElement(By.xpath("//input[@placeholder='Presidente']"));
        assertNotNull(presidentField, "El campo de presidente no se encontró.");
        presidentField.sendKeys("Juan Pérez");
        assertEquals("Juan Pérez", presidentField.getAttribute("value"),
                "El campo de presidente no se ha actualizado correctamente.");
    }

    @Test
    public void testCreateLeagueButton() {
        testLeagueFormFields();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Crear Liga']")));
        assertNotNull(createButton, "El botón 'Crear Liga' no está presente.");

        createButton.click();
        
        WebDriverWait waitForLeague = new WebDriverWait(driver, Duration.ofSeconds(1));
        waitForLeague.until(ExpectedConditions.urlContains("/leagues/"));

        assertTrue(driver.getCurrentUrl().contains("/leagues/"), "La URL de la liga no es la correcta.");
        System.out.println("Acceso correcto a la liga " + driver.getCurrentUrl());
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLeagueFormFields();

        WebElement cancelButton = driver.findElement(By.xpath("//button[text()='Cancelar']"));
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        cancelButton.click();

        assertTrue(driver.getCurrentUrl().contains("/leagues"), "La URL de la liga no es la correcta.");
        System.out.println("Acceso correcto a las ligas " + driver.getCurrentUrl());
    }
}
