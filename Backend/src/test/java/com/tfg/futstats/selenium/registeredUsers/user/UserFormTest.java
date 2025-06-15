package com.tfg.futstats.selenium.registeredUsers.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.io.File;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import com.tfg.futstats.selenium.BaseTest;

public class UserFormTest extends BaseTest {

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        scrollToElement(usernameField);
        scrollToElement(passwordField);
        scrollToElement(loginButton);

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("user0");
        passwordField.sendKeys("pass");
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }

    @Test
    public void testUserFormLoaded() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement formTitle = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Nuevo Usuario')]")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El título del formulario 'Nuevo Usuario' no se muestra.");

        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Contraseña']")));

        scrollToElement(nameField);
        scrollToElement(passwordField);

        assertNotNull(nameField, "El campo 'Nombre' no está presente.");
        assertNotNull(passwordField, "El campo 'Contraseña' no está presente.");
    }

    @Test
    public void testUserFormFieldsInteraction() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Contraseña']")));

        scrollToElement(nameField);
        scrollToElement(passwordField);

        nameField.clear();
        passwordField.clear();
        nameField.sendKeys("Juan Pérez");
        passwordField.sendKeys("contraseña123");

        assertEquals("Juan Pérez", nameField.getAttribute("value"),
                "El campo 'Nombre' no se ha actualizado correctamente.");
        assertEquals("contraseña123", passwordField.getAttribute("value"),
                "El campo 'Contraseña' no se ha actualizado correctamente.");
    }

    @Test
    public void testCancelButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Contraseña']")));

        scrollToElement(nameField);
        scrollToElement(passwordField);

        WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Cancelar')]")));
        
        scrollToElement(cancelButton);
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelButton.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }
    }

    @Test
    public void testSaveButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Contraseña']")));

        scrollToElement(nameField);
        scrollToElement(passwordField);

        nameField.clear();
        passwordField.clear();
        nameField.sendKeys("Juan Pérez 3");
        passwordField.sendKeys("contraseña1234");

        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Guardar')]")));
        
        scrollToElement(saveButton);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }

        wait.until(ExpectedConditions.urlContains("/leagues"));
        assertTrue(driver.getCurrentUrl().contains("/leagues"), "La URL del usuario no es la correcta.");
    }

    @Test
    public void testImageUploadFunctionality() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement imageFileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@type='file']")));
        scrollToElement(imageFileInput);
        assertNotNull(imageFileInput, "El campo de carga de imagen no está presente.");

        File file = new File("src/main/resources/static/no_image.jpg");
        imageFileInput.sendKeys(file.getAbsolutePath());
    }
}
