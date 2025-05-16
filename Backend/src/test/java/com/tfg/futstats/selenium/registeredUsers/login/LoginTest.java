package com.tfg.futstats.selenium.registeredUsers.login;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginFormVisibility() {
        driver.get("https://localhost:" + this.port + "/leagues/");
        
        WebElement loginForm = driver.findElement(By.className("navbar-form"));
        assertNotNull(loginForm, "El formulario de inicio de sesión no se muestra.");

        // Verificar que los campos de entrada para nombre y contraseña estén presentes
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        assertNotNull(usernameField, "El campo 'Nombre' no está presente.");
        assertNotNull(passwordField, "El campo 'Contraseña' no está presente.");

        // Verificar que el botón de "Iniciar Sesión" y "Crear Usuario" estén presentes
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));
        WebElement createUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Usuario')]"));
        assertNotNull(loginButton, "El botón 'Iniciar Sesión' no está presente.");
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues/");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("user0");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testUserLoggedIn() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Usuario Actual:')]")));

        WebElement userLabel = driver.findElement(By.xpath("//a[contains(text(), 'Usuario Actual:')]"));
        assertNotNull(userLabel, "El nombre de usuario no se muestra después de iniciar sesión.");

        WebElement profileButton = driver.findElement(By.xpath("//button[contains(text(), 'Mi perfil')]"));
        assertNotNull(profileButton, "El botón 'Mi perfil' no está presente.");
    }

    @Test
    public void testLogout() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Cerrar Sesión')]")));

        WebElement logoutButton = driver.findElement(By.xpath("//button[contains(text(), 'Cerrar Sesión')]"));
        assertNotNull(logoutButton, "El botón 'Cerrar Sesión' no está presente.");

        logoutButton.click();

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));
        WebElement createUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Usuario')]"));
        assertNotNull(loginButton, "El botón 'Iniciar Sesión' no está presente.");
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testCreateUserButton() {
        driver.get("https://localhost:" + this.port + "/login");

        // Verificar que el botón "Crear Usuario" está presente
        WebElement createUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Usuario')]"));
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");

        // Hacer clic en el botón "Crear Usuario"
        createUserButton.click();

        // Esperar a que la página se actualice y redirija a la página de creación de
        // usuario
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("/users/new")); // Ajustar la URL de destino según sea necesario.

        // Verificar que estamos en la página de creación de usuario
        assertTrue(driver.getCurrentUrl().contains("/users/new"), "No redirige a la página de creación de usuario.");
    }

}
