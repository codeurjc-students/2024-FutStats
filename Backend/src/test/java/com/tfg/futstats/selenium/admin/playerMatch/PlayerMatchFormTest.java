package com.tfg.futstats.selenium.admin.playerMatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class PlayerMatchFormTest extends BaseTest {
    @Test
    public void testLoginFunctionality() {
        driver.get("http://localhost:" + this.port + "/playerMatch/1");

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
    public void testPlayerMatchFormLoaded() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        editButton.click();
        System.out.println("Clic en botón 'Editar Estadística de Jugador'.");

        // Verificar que el título del formulario esté presente
        WebElement formTitle = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(formTitle, "El formulario de creación de estadísticas del jugador no se muestra correctamente.");

        // Verificar que el campo de selección de partido esté presente
        WebElement matchSelect = driver.findElement(By.xpath("//select[@name='match']"));
        assertNotNull(matchSelect, "El campo de selección de partido no está presente.");

        // Verificar que el campo de selección de jugador esté presente
        WebElement playerSelect = driver.findElement(By.xpath("//select[@name='player']"));
        assertNotNull(playerSelect, "El campo de selección de jugador no está presente.");

        // Verificar que los campos de estadísticas ofensivas estén presentes
        WebElement shoots = driver.findElement(By.xpath("//input[@id='shoots']"));
        WebElement goals = driver.findElement(By.xpath("//input[@id='goals']"));
        assertNotNull(shoots, "El campo 'Tiros' no está presente.");
        assertNotNull(goals, "El campo 'Goles' no está presente.");
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        editButton.click();
        System.out.println("Clic en botón 'Editar Estadística de Jugador'.");

        WebElement cancelButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Cancelar')]")));
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        cancelButton.click();

        assertTrue(driver.getCurrentUrl().contains("/playerMatch/"), "La URL del partido no es la correcta.");
        System.out.println("Acceso correcto al partido " + driver.getCurrentUrl());
    }

    @Test
    public void testSaveButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        editButton.click();
        System.out.println("Clic en botón 'Editar Estadística de Jugador'.");

        // Verificar que el botón "Crear Estadística de Jugador" esté presente
        WebElement saveButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Crear Estadística de Jugador')]")));
        assertNotNull(saveButton, "El botón 'Crear Estadística de Jugador' no está presente.");

        saveButton.click();

        assertTrue(driver.getCurrentUrl().contains("/playerMatch/"), "La URL del jugador no es la correcta.");
        System.out.println("Acceso correcto al jugador " + driver.getCurrentUrl());
    }

}
