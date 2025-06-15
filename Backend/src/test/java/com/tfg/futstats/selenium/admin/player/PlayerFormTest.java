package com.tfg.futstats.selenium.admin.player;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class PlayerFormTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        // Esperar a que el formulario de login esté visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("navbar-form")));
        
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
        
        try {
            loginButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Test
    public void testPlayerFormLoaded() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement formTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El título del formulario 'Nuevo Jugador' no se muestra.");

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nombre']")));
        WebElement ageField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Edad']")));
        WebElement positionField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Posición']")));
        WebElement nationalityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nacionalidad']")));

        scrollToElement(nameField);
        scrollToElement(ageField);
        scrollToElement(positionField);
        scrollToElement(nationalityField);

        assertNotNull(nameField, "El campo 'Nombre' no está presente.");
        assertNotNull(ageField, "El campo 'Edad' no está presente.");
        assertNotNull(positionField, "El campo 'Posición' no está presente.");
        assertNotNull(nationalityField, "El campo 'Nacionalidad' no está presente.");
    }

    @Test
    public void testPlayerFormFieldsInteraction() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement formTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El título del formulario 'Nuevo Jugador' no se muestra.");

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nombre']")));
        WebElement ageField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Edad']")));
        WebElement positionField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Posición']")));
        WebElement nationalityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nacionalidad']")));

        scrollToElement(nameField);
        scrollToElement(ageField);
        scrollToElement(positionField);
        scrollToElement(nationalityField);

        nameField.clear();
        ageField.clear();
        positionField.clear();
        nationalityField.clear();

        nameField.sendKeys("Vinicius Jr.");
        ageField.sendKeys("22");
        positionField.sendKeys("Delantero");
        nationalityField.sendKeys("Brasil");
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Cancel')]")));
        scrollToElement(cancelButton);
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        
        try {
            cancelButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
        }

        System.out.println("Clic en botón 'Cancelar'.");

        WebElement playerListHeader = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(playerListHeader);
        assertNotNull(playerListHeader, "No se ha redirigido correctamente.");
    }

    @Test
    public void testSaveButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Save')]")));
        scrollToElement(saveButton);
        assertNotNull(saveButton, "El botón 'Save' no está presente.");

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nombre']")));
        WebElement ageField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Edad']")));
        WebElement positionField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Posición']")));
        WebElement nationalityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nacionalidad']")));

        scrollToElement(nameField);
        scrollToElement(ageField);
        scrollToElement(positionField);
        scrollToElement(nationalityField);

        nameField.clear();
        ageField.clear();
        positionField.clear();
        nationalityField.clear();

        nameField.sendKeys("Vinicius Jr.");
        ageField.sendKeys("22");
        positionField.sendKeys("Delantero");
        nationalityField.sendKeys("Brasil");

        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        
        try {
            saveButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
        }

        System.out.println("Clic en botón 'Save'.");
    }

    private boolean isEditingPlayer() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        return driver.findElements(By.xpath("//h2[contains(text(), 'Nuevo Jugador')]")).isEmpty();
    }

}
