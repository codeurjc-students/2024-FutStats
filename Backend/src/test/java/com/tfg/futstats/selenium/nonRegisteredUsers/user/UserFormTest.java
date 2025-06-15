package com.tfg.futstats.selenium.nonRegisteredUsers.user;

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
        // Primero navegamos a la página de usuarios
        driver.get("https://localhost:" + this.port + "/leagues");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperamos y hacemos scroll hasta el botón de crear usuario
        WebElement newUser = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Crear Usuario')]")));
        
        // Aseguramos que el botón esté visible y clickeable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", newUser);
        try {
            Thread.sleep(1000); // Esperamos a que el scroll termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Esperamos a que el botón sea clickeable
        wait.until(ExpectedConditions.elementToBeClickable(newUser));
        
        // Intentamos hacer click en el botón
        try {
            newUser.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newUser);
        }

        // Esperamos y hacemos scroll hasta el botón de cancelar
        WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Cancelar')]")));
        
        // Aseguramos que el botón esté visible y clickeable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", cancelButton);
        try {
            Thread.sleep(1000); // Esperamos a que el scroll termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Esperamos a que el botón sea clickeable
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        
        // Intentamos hacer click en el botón
        try {
            cancelButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
        }

        // Verificamos que se ha redirigido correctamente a la lista de usuarios
        wait.until(ExpectedConditions.urlContains("/leagues"));
        WebElement userListHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(), 'Ligas')]")));
        
        assertNotNull(userListHeader, "No se ha redirigido correctamente a la lista de usuarios.");
        assertTrue(driver.getCurrentUrl().contains("/leagues"), "La URL no es la correcta después de cancelar.");
    }

    @Test
    public void testSaveButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Fill in the form fields
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Nombre']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@placeholder='Contraseña']")));

        scrollToElement(nameField);
        scrollToElement(passwordField);

        nameField.clear();
        passwordField.clear();
        nameField.sendKeys("Juan Pérez 2");
        passwordField.sendKeys("contraseña123");

        // Find and scroll to the save button
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Guardar')]")));
        
        // Ensure the button is in view and clickable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", saveButton);
        try {
            Thread.sleep(1000); // Wait for scroll to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        
        // Try multiple approaches to click the button
        try {
            // First attempt: regular click
            saveButton.click();
        } catch (Exception e) {
            try {
                // Second attempt: JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
            } catch (Exception e2) {
                // Third attempt: Actions click
                new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(saveButton)
                    .click()
                    .perform();
            }
        }

        // Verify the redirect
        wait.until(ExpectedConditions.urlContains("/leagues"));
        assertTrue(driver.getCurrentUrl().contains("/leagues"), "La URL del usuario no es la correcta.");
    }

    @Test
    public void testImageUploadFunctionality() {
        driver.get("https://localhost:" + this.port + "/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperar y hacer scroll hasta el campo de carga de imagen
        WebElement imageFileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@type='file']")));
        scrollToElement(imageFileInput);
        assertNotNull(imageFileInput, "El campo de carga de imagen no está presente.");

        File file = new File("src/main/resources/static/no_image.jpg");
        imageFileInput.sendKeys(file.getAbsolutePath());
    }
}
