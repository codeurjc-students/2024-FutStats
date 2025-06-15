package com.tfg.futstats.selenium.registeredUsers.league;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import com.tfg.futstats.selenium.BaseTest;

public class LeagueDetailTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/leagues/1");

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
        
        usernameField.sendKeys("user0");
        passwordField.sendKeys("pasS123");
        
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        
        try {
            loginButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Test
    public void testLeagueInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement leagueName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(leagueName);
        assertNotNull(leagueName, "El nombre de la liga no se muestra.");
    }

    @Test
    public void testAddLeagueToFavoritesButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//button[contains(text(), 'Añadir Liga a favoritos')]")));

        WebElement addFavoriteButton = driver
                .findElement(By.xpath("//button[contains(text(), 'Añadir Liga a favoritos')]"));
        scrollToElement(addFavoriteButton);
        assertNotNull(addFavoriteButton, "El botón 'Añadir Liga a favoritos' no está visible.");

        addFavoriteButton.click();
    }

    @Test
    public void testTeamsPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        List<WebElement> teamItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li")));
        assertTrue(teamItems.size() > 0, "No hay equipos disponibles para paginar.");

        System.out.println("Número inicial de equipos mostrados: " + teamItems.size());

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//pagination-controls[@id='team']//a[contains(@class, 'pagination-next')]")));
                scrollToElement(nextButton);
                nextButton.click();
                System.out.println("Clic en botón 'Siguiente' de equipos.");
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de equipos.");
                break; 
            }
        }
    }

    
        @Test
        public void testAccessToTeam() {

                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                List<WebElement> teamLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.xpath("//ul[@class='items']/li/a[not(contains(@href, '/matches/'))]")));
                assertTrue(teamLinks.size() > 0, "No se encontraron equipos en la lista.");

                WebElement teamLink = teamLinks.get(0);
                teamLink.click();

                WebDriverWait waitForTeam = new WebDriverWait(driver, Duration.ofSeconds(1));
                waitForTeam.until(ExpectedConditions.urlContains("/teams/"));

                assertTrue(driver.getCurrentUrl().contains("/teams/"), "La URL del equipo no es la correcta.");
                System.out.println("Acceso correcto al equipo: " + driver.getCurrentUrl());

        }

    @Test
    public void testMatchesPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        List<WebElement> matchItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li")));
        assertTrue(matchItems.size() > 0, "No hay partidos disponibles para paginar.");

        System.out.println("Número inicial de partidos mostrados: " + matchItems.size());

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//pagination-controls[@id='match']//a[contains(@class, 'pagination-next')]")));
                scrollToElement(nextButton);
                nextButton.click();
                System.out.println("Clic en botón 'Siguiente' de partidos.");
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de partidos.");
                break;
            }
        }
    }

    @Test
        public void testAccessToMatch() {

                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                List<WebElement> teamLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.xpath("//ul[@class='items']/li/a[not(contains(@href, '/matches/'))]")));
                assertTrue(teamLinks.size() > 0, "No se encontraron equipos en la lista.");

                WebElement teamLink = teamLinks.get(0);
                teamLink.click();

                WebDriverWait waitForTeam = new WebDriverWait(driver, Duration.ofSeconds(1));
                waitForTeam.until(ExpectedConditions.urlContains("/teams/"));

                assertTrue(driver.getCurrentUrl().contains("/teams/"), "La URL del equipo no es la correcta.");
                System.out.println("Acceso correcto al equipo: " + driver.getCurrentUrl());

        }
}
