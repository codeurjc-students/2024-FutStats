package com.tfg.futstats.selenium.admin.league;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import com.tfg.futstats.selenium.BaseTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueListTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
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
    public void testHomePageLoadsSuccessfully() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        String title = driver.getTitle();
        assertEquals("Fut-Stats", title, "El título de la página no es el esperado.");
    }

    @Test
    public void testLeagueListIsDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement leagueList = driver.findElement(By.className("items"));
        assertNotNull(leagueList, "La lista de ligas no se encontró.");
        assertTrue(leagueList.findElements(By.tagName("li")).size() > 0, "La lista de ligas está vacía.");
    }

    @Test
    public void testPaginationControls() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement paginationControls = driver.findElement(By.tagName("pagination-controls"));
        assertNotNull(paginationControls, "El componente de controles de paginación no se encontró.");

        WebElement nextButton = paginationControls.findElement(By.xpath(".//a[contains(text(), 'Siguiente')]"));
        assertNotNull(nextButton, "El enlace 'Siguiente' no se encontró.");
        nextButton.click();

        System.out.println("Botón 'Siguiente' funciona correctamente.");
    }

    @Test
    public void testCreateLeagueButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Crear una liga')]")));

        WebElement createButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Crear una liga')]")));

        scrollToElement(createButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(createButton));
        assertNotNull(createButton, "El botón 'Crear Liga' no está presente.");

        createButton.click();

        wait.until(ExpectedConditions.urlContains("/leagues/new"));
    }

    @Test
    public void testAdminOptionsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//h3[contains(text(), 'Opciones de Administrador')]")));

        WebElement adminOptions = driver.findElement(By.xpath("//h3[contains(text(), 'Opciones de Administrador')]"));
        assertTrue(adminOptions.isDisplayed(), "Las opciones de administrador no están visibles.");
    }
}
