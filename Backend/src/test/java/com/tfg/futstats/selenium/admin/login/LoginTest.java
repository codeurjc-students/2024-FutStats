package com.tfg.futstats.selenium.admin.login;

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
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testLoginFormVisibility() {
        driver.get("https://localhost:" + this.port + "/leagues");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("input[name='username']")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("input[name='password']")));
        
        scrollToElement(usernameField);
        scrollToElement(passwordField);
        
        assertNotNull(usernameField, "El campo 'Nombre' no está presente.");
        assertNotNull(passwordField, "El campo 'Contraseña' no está presente.");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[type='submit']")));
        WebElement createUserButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Crear Usuario')]")));
            
        scrollToElement(loginButton);
        scrollToElement(createUserButton);
        
        assertNotNull(loginButton, "El botón 'Iniciar Sesión' no está presente.");
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='username']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='password']")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[type='submit']")));

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
    public void testUserLoggedIn() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement userLabel = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("a.navbar-brand")));
        assertNotNull(userLabel, "El nombre de usuario no se muestra después de iniciar sesión.");
        assertTrue(userLabel.getText().contains("Usuario Actual:"), "El texto del usuario no es correcto");

        WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        WebElement usersButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Ver Usuarios')]")));
        assertNotNull(profileButton, "El botón 'Mi perfil' no está presente.");
        assertNotNull(usersButton, "El botón 'Ver Usuarios' no está presente.");
    }

    @Test
    public void testLogout() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Cerrar Sesión')]")));
        assertNotNull(logoutButton, "El botón 'Cerrar Sesión' no está presente.");
        logoutButton.click();

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[type='submit']")));
        WebElement createUserButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Crear Usuario')]")));
        assertNotNull(loginButton, "El botón 'Iniciar Sesión' no está presente.");
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testCreateUserButton() {
        driver.get("https://localhost:" + this.port + "/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement createUserButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Crear Usuario')]")));
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
        createUserButton.click();

        wait.until(ExpectedConditions.urlContains("/users/new"));
        assertTrue(driver.getCurrentUrl().contains("/users/new"), "No redirige a la página de creación de usuario.");
    }
}
