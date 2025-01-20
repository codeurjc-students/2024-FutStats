package com.tfg.futstats.selenium.nonRegisteredUsers.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testLeagueInfoDisplayed() {
        driver.get("http://localhost:4200/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement leagueName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(leagueName, "El nombre de la liga no se muestra.");
    }

    @Test
    public void testTeamsListPagination() {
        driver.get("http://localhost:4200/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        WebElement paginationControls = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pagination-controls")));
        assertNotNull(paginationControls, "El componente de controles de paginación no se encontró.");

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//pagination-controls//li[contains(@class, 'pagination-next')]//a[contains(text(), 'Siguiente')]")));
        assertNotNull(nextButton, "El enlace 'Siguiente' no se encontró.");

        nextButton.click();

        System.out.println("Botón 'Siguiente' funciona correctamente.");
    }

    @Test
    public void testGoBackButton() {
        driver.get("http://localhost:4200/leagues/1");

        WebElement goBackButton = driver.findElement(By.xpath("//button[contains(text(), 'Volver')]"));
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        goBackButton.click();

        assertEquals("http://localhost:4200/leagues", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }

    @Test
    public void testTeamsPagination() {
        driver.get("http://localhost:4200/leagues/1"); 

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
                Thread.sleep(1000); 
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de equipos.");
                break; 
            }
        }
    }

    @Test
    public void testAccessToTeam() {
        driver.get("http://localhost:4200/leagues/1");

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

        driver.navigate().back();
    }

    @Test
    public void testMatchesPagination() {
        driver.get("http://localhost:4200/leagues/1");

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
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de partidos.");
                break;
            }
        }
    }

    @Test
    public void testAccessToMatch() {
        driver.get("http://localhost:4200/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        List<WebElement> matchLinks = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//ul[@class='items']/li/a[contains(@href, '/matches/')]")));
        assertTrue(matchLinks.size() > 0, "No se encontraron partidos en la lista.");

        WebElement matchLink = matchLinks.get(0);
        matchLink.click();

        WebDriverWait waitForMatch = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForMatch.until(ExpectedConditions.urlContains("/matches/"));

        assertTrue(driver.getCurrentUrl().contains("/matches/"), "La URL del partido no es la correcta.");
        System.out.println("Acceso correcto al partido: " + driver.getCurrentUrl());

        driver.navigate().back();
    }
}
