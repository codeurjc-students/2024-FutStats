package com.tfg.futstats.selenium.admin.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class UserDetailTest extends BaseTest {

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Esperar y hacer scroll hasta los elementos de login
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        // Hacer scroll hasta los elementos y esperar un momento
        scrollToElement(usernameField);
        scrollToElement(passwordField);
        scrollToElement(loginButton);

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }

    @Test
    public void testUserInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar y hacer scroll hasta el nombre de usuario
        WebElement userName = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'admin')]")));
        scrollToElement(userName);
        assertNotNull(userName, "El nombre del usuario no se muestra correctamente.");
    }

    @Test
    public void testLeaguesTeamsPlayersDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar y hacer scroll hasta los elementos de ligas, equipos y jugadores
        WebElement firstLeague = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[h3[contains(text(),'Ligas')]]")));
        WebElement firstTeam = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[h3[contains(text(),'Equipos')]]")));
        WebElement firstPlayer = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[h3[contains(text(),'Jugadores')]]")));

        scrollToElement(firstLeague);
        scrollToElement(firstTeam);
        scrollToElement(firstPlayer);
    }

    @Test
    public void testRemoveUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar y hacer scroll hasta el botón de borrar usuario
        WebElement removeUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Borrar User')]")));
        scrollToElement(removeUserButton);
        assertNotNull(removeUserButton, "El botón 'Borrar Usuario' no está presente.");
    }

    @Test
    public void testEditUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar y hacer scroll hasta el botón de editar usuario
        WebElement editUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Editar Usuario')]")));
        scrollToElement(editUserButton);
        assertNotNull(editUserButton, "El botón 'Editar Usuario' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar y hacer scroll hasta el botón de volver
        WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(backButton);
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        // Esperar a que el elemento sea clickeable después del scroll
        wait.until(ExpectedConditions.elementToBeClickable(backButton));

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        // Verificar que se ha redirigido correctamente
        WebElement previousPageHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(), 'Ligas')]")));
        scrollToElement(previousPageHeader);
        assertNotNull(previousPageHeader, "No se ha redirigido correctamente.");
    }
}
