package com.tfg.futstats.selenium.registeredUsers.user;

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
        usernameField.sendKeys("user0");
        passwordField.sendKeys("pasS123");
        
        loginButton.click();
    }

    @Test
    public void testUserInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement myProfile = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));

        scrollToElement(myProfile);

        wait.until(ExpectedConditions.elementToBeClickable(myProfile));
        assertNotNull(myProfile, "El botón de mi perfil no se muestra correctamente."); 

        myProfile.click();

        wait.until(ExpectedConditions.urlContains("/myProfile"));
        
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/myProfile"), 
            "No se ha redirigido a la página de perfil. URL actual: " + currentUrl);

        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(), 'user0')]")));
        assertNotNull(userName, "El nombre del usuario no se muestra correctamente.");
    }

    @Test
    public void testLeaguesTeamsPlayersDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        
        scrollToElement(myProfile);

        wait.until(ExpectedConditions.elementToBeClickable(myProfile));
        assertNotNull(myProfile, "El botón de mi perfil no se muestra correctamente."); 
        
        myProfile.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container")));

        WebElement leaguesSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Ligas')]/..")));
        
        scrollToElement(leaguesSection);

        wait.until(ExpectedConditions.elementToBeClickable(leaguesSection));
        assertNotNull(leaguesSection, "La sección de ligas no está presente.");

        WebElement teamsSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Equipos')]/..")));
        
        scrollToElement(teamsSection);

        wait.until(ExpectedConditions.elementToBeClickable(teamsSection));
        assertNotNull(teamsSection, "La sección de equipos no está presente.");

        WebElement playersSection = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'container')]//h3[contains(text(), 'Jugadores')]/..")));
        
        scrollToElement(playersSection);

        wait.until(ExpectedConditions.elementToBeClickable(playersSection));
        assertNotNull(playersSection, "La sección de jugadores no está presente.");
    }

    @Test
    public void testEditUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        
        scrollToElement(myProfile);

        wait.until(ExpectedConditions.elementToBeClickable(myProfile));
        assertNotNull(myProfile, "El botón de mi perfil no se muestra correctamente."); 

        myProfile.click();

        wait.until(ExpectedConditions.urlContains("/myProfile"));
        
        WebElement editUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Editar Usuario')]")));

        scrollToElement(editUserButton);
        assertNotNull(editUserButton, "El botón 'Editar Usuario' no está presente.");
    }

    @Test
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement myProfile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Mi perfil')]")));
        scrollToElement(myProfile);
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");
        myProfile.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container")));

        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(goBackButton);
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        goBackButton.click();

        wait.until(ExpectedConditions.urlContains("/leagues"));
        assertEquals("https://localhost:" + this.port + "/leagues", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }
}
