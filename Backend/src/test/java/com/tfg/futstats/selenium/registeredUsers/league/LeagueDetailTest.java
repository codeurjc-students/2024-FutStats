package com.tfg.futstats.selenium.registeredUsers.league;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import com.tfg.futstats.selenium.BaseTest;

public class LeagueDetailTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("http://localhost:" + this.port + "/leagues/1");

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
    public void testLeagueInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement leagueName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
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
        List<WebElement> matchLinks = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='items']/li/a[contains(@href, '/matches/')]")));
        assertTrue(matchLinks.size() > 0, "No se encontraron partidos en la lista.");

        WebElement matchLink = matchLinks.get(0);
        matchLink.click();

        WebDriverWait waitForMatch = new WebDriverWait(driver, Duration.ofSeconds(1));
        waitForMatch.until(ExpectedConditions.urlContains("/matches/"));

        assertTrue(driver.getCurrentUrl().contains("/matches/"), "La URL del partido no es la correcta.");
        System.out.println("Acceso correcto al partido: " + driver.getCurrentUrl());
    }
}
