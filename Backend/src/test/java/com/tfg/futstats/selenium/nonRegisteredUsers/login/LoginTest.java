package com.tfg.futstats.selenium.nonRegisteredUsers.login;

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

public class LoginTest extends BaseTest {

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
    public void testLoginFormVisibility() {
        driver.get("https://localhost:" + this.port + "/leagues");
        
        WebElement loginForm = driver.findElement(By.className("navbar-form"));
        assertNotNull(loginForm, "El formulario de inicio de sesión no se muestra.");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        assertNotNull(usernameField, "El campo 'Nombre' no está presente.");
        assertNotNull(passwordField, "El campo 'Contraseña' no está presente.");

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));
        WebElement createUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Usuario')]"));
        assertNotNull(loginButton, "El botón 'Iniciar Sesión' no está presente.");
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testCreateUserButton() {
        driver.get("https://localhost:" + this.port + "/leagues");

        WebElement createUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Usuario')]"));
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");

        createUserButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("/users/new"));

        assertTrue(driver.getCurrentUrl().contains("/users/new"), "No redirige a la página de creación de usuario.");
    }
}
