package com.tfg.futstats.selenium.nonRegisteredUsers.login;

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
        driver.get("http://localhost:4200/leagues/");
        
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
    public void testCreateUserButton() {
        driver.get("http://localhost:4200/login");

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
