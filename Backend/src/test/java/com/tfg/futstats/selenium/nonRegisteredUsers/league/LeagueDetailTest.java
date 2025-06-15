package com.tfg.futstats.selenium.nonRegisteredUsers.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testLeagueInfoDisplayed() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement leagueName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(leagueName);
        assertNotNull(leagueName, "El nombre de la liga no se muestra.");
    }

    @Test
    public void testTeamsListPagination() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        WebElement paginationControls = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pagination-controls")));
        scrollToElement(paginationControls);
        assertNotNull(paginationControls, "El componente de controles de paginación no se encontró.");

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//pagination-controls//li[contains(@class, 'pagination-next')]//a[contains(text(), 'Siguiente')]")));
        scrollToElement(nextButton);
        assertNotNull(nextButton, "El enlace 'Siguiente' no se encontró.");

        nextButton.click();

        System.out.println("Botón 'Siguiente' funciona correctamente.");
    }

    @Test
    public void testGoBackButton() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement goBackButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(goBackButton);
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        goBackButton.click();

        assertEquals("https://localhost:" + this.port + "/leagues", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }

    @Test
    public void testTeamsPagination() {
        driver.get("https://localhost:" + this.port + "/leagues/1"); 

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
                Thread.sleep(1000); 
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de equipos.");
                break; 
            }
        }
    }

    @Test
    public void testAccessToTeam() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

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
        driver.get("https://localhost:" + this.port + "/leagues/1");

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
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de partidos.");
                break;
            }
        }
    }

    @Test
    public void testAccessToMatch() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> matchLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li/a[contains(@href, '/matches/')]")));
        assertTrue(matchLinks.size() > 0, "No se encontraron partidos en la lista.");

        WebElement matchLink = matchLinks.get(0);
        scrollToElement(matchLink);
        
        wait.until(ExpectedConditions.elementToBeClickable(matchLink));
        
        try {
            matchLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", matchLink);
        }

        WebDriverWait waitForMatch = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitForMatch.until(ExpectedConditions.urlContains("/matches/"));

        assertTrue(driver.getCurrentUrl().contains("/matches/"), "La URL del partido no es la correcta.");
        System.out.println("Acceso correcto al partido: " + driver.getCurrentUrl());
    }
}
