package com.tfg.futstats.selenium.admin.playerMatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class PlayerMatchFormTest extends BaseTest {
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(1000); // Aumentamos el tiempo de espera
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Esperar a que el elemento sea clickeable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

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
        
        try {
            loginButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Test
    public void testPlayerMatchFormLoaded() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement formTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El título del formulario no se muestra.");

        WebElement matchSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//select[@name='match']")));
        WebElement playerSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//select[@name='player']")));
        WebElement shoots = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='shoots']")));
        WebElement goals = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='goals']")));

        scrollToElement(matchSelect);
        scrollToElement(playerSelect);
        scrollToElement(shoots);
        scrollToElement(goals);

        assertNotNull(matchSelect, "El campo de selección de partido no está presente.");
        assertNotNull(playerSelect, "El campo de selección de jugador no está presente.");
        assertNotNull(shoots, "El campo 'Tiros' no está presente.");
        assertNotNull(goals, "El campo 'Goles' no está presente.");
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Cancelar')]")));
        scrollToElement(cancelButton);
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        
        try {
            cancelButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
        }

        assertTrue(driver.getCurrentUrl().contains("/playerMatch/"), "La URL del partido no es la correcta.");
    }

    @Test
    public void testSaveButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        
        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Crear Estadística de Jugador')]")));
        scrollToElement(saveButton);
        assertNotNull(saveButton, "El botón 'Crear Estadística de Jugador' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        
        try {
            saveButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
        }

        assertTrue(driver.getCurrentUrl().contains("/playerMatch/"), "La URL del jugador no es la correcta.");
    }

    @Test
    public void testPlayerMatchFormVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Primero esperamos y hacemos click en el botón de editar
        WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        // Esperamos a que el formulario se cargue
        WebElement formTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El título del formulario no se muestra.");

        // Verificamos los selectores
        WebElement playerSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//select[@name='player']")));
        WebElement matchSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//select[@name='match']")));

        scrollToElement(playerSelect);
        scrollToElement(matchSelect);

        assertNotNull(playerSelect, "El selector de jugador no está presente.");
        assertNotNull(matchSelect, "El selector de partido no está presente.");
    }

    @Test
    public void testPlayerMatchFormButtons() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Primero esperamos y hacemos click en el botón de editar
        WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }

        // Esperamos a que el formulario se cargue y verificar los botones
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Crear Estadística de Jugador')]")));
        WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Cancelar')]")));

        scrollToElement(saveButton);
        scrollToElement(cancelButton);

        assertNotNull(saveButton, "El botón 'Crear Estadística de Jugador' no está presente.");
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");
    }
}
