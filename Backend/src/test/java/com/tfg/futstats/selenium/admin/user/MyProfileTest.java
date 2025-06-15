package com.tfg.futstats.selenium.admin.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class MyProfileTest extends BaseTest {

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

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

        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        // Intentamos hacer click en el botón
        try {
            loginButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Test
    public void testUserInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperar a que el botón de perfil esté visible y hacer click
        WebElement myProfile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El botón de mi perfil no se muestra correctamente.");

        scrollToElement(myProfile);
        try {
            myProfile.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);
        }

        // Esperar a que la URL cambie a /myProfile
        wait.until(ExpectedConditions.urlContains("/myProfile"));
        
        // Verificar que estamos en la página correcta
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/myProfile"), 
            "No se ha redirigido a la página de perfil. URL actual: " + currentUrl);

        // Esperar y verificar que se muestra la información del usuario
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(), 'admin')]")));
        assertNotNull(userName, "El nombre del usuario no se muestra correctamente.");
    }

    @Test
    public void testLeaguesTeamsPlayersDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        
        // Aseguramos que el botón esté visible y clickeable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", myProfile);
        try {
            Thread.sleep(1000); // Esperamos a que el scroll termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Esperamos a que el botón sea clickeable
        wait.until(ExpectedConditions.elementToBeClickable(myProfile));
        
        // Intentamos hacer click en el botón
        try {
            myProfile.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);
        }

        // Esperar a que el contenedor del usuario esté presente
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container")));

        // Esperar y hacer scroll hasta las secciones de ligas, equipos y jugadores
        WebElement leaguesSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Ligas')]/..")));
        
        // Aseguramos que la sección esté visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", leaguesSection);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertNotNull(leaguesSection, "La sección de ligas no está presente.");

        WebElement teamsSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Equipos')]/..")));
        
        // Aseguramos que la sección esté visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", teamsSection);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertNotNull(teamsSection, "La sección de equipos no está presente.");

        WebElement playersSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Jugadores')]/..")));
        
        // Aseguramos que la sección esté visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", playersSection);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertNotNull(playersSection, "La sección de jugadores no está presente.");
    }

    @Test
    public void testEditUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperar y hacer scroll hasta el botón de mi perfil
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        
        // Aseguramos que el botón esté visible y clickeable
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", myProfile);
        try {
            Thread.sleep(1000); // Esperamos a que el scroll termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Esperamos a que el botón sea clickeable
        wait.until(ExpectedConditions.elementToBeClickable(myProfile));
        
        // Intentamos hacer click en el botón
        try {
            myProfile.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);
        }

        // Esperar y hacer scroll hasta el botón de editar usuario
        WebElement editUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Editar Usuario')]")));
        scrollToElement(editUserButton);
        assertNotNull(editUserButton, "El botón 'Editar Usuario' no está presente.");
    }

    @Test
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Esperar a que el botón de perfil esté presente y hacer click
        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", myProfile);

        // Esperar a que el contenedor del usuario esté presente
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container")));

        // Esperar a que el botón volver esté presente y hacer click
        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(goBackButton);
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        // Asegurarse de que el botón está visible y clickeable
        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);

        // Verificar la redirección
        wait.until(ExpectedConditions.urlContains("/leagues"));
        assertEquals("https://localhost:" + this.port + "/leagues", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }
}
