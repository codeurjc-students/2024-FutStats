package com.tfg.futstats.selenium.registeredUsers.match;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;
import java.util.List;

public class MatchDetailTest extends BaseTest {

    @Test
    public void testMatchDetailVisibility() {
        driver.get("http://localhost:4200/matches/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement matchName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(matchName, "El nombre del partido no se muestra.");

        WebElement team1Stats = driver.findElement(By.xpath("//p[contains(text(), 'Disparos')]"));
        assertNotNull(team1Stats, "Las estadísticas del primer equipo no se muestran.");

        WebElement team2Stats = driver.findElement(By.xpath("//p[contains(text(), 'Goles')]"));
        assertNotNull(team2Stats, "Las estadísticas del segundo equipo no se muestran.");
    }

    @Test
    public void testMatchStatistics() {
        driver.get("http://localhost:4200/matches/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement team1Shoots = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Disparos')]")));
        assertNotNull(team1Shoots, "Los disparos del primer equipo no se muestran.");

        WebElement team1Goals = driver.findElement(By.xpath("//p[contains(text(), 'Goles:')]"));
        assertNotNull(team1Goals, "Los goles del primer equipo no se muestran.");

        WebElement team2Shoots = driver.findElement(By.xpath("//p[contains(text(), 'Disparos:')]"));
        assertNotNull(team2Shoots, "Los disparos del segundo equipo no se muestran.");

        WebElement team2Goals = driver.findElement(By.xpath("//p[contains(text(), 'Goles:')]"));
        assertNotNull(team2Goals, "Los goles del segundo equipo no se muestran.");
    }

    @Test
    public void testPlayerMatchesPagination() {
        driver.get("http://localhost:4200/matches/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        List<WebElement> playerItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li")));

        assertTrue(playerItems.size() > 0, "No hay jugadores disponibles para paginar.");

        System.out.println("Número inicial de jugadores mostrados: " + playerItems.size());

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//pagination-controls//a[contains(@class, 'pagination-next')]")));
                nextButton.click();
                System.out.println("Clic en botón 'Siguiente' de jugadores.");

                wait.until(ExpectedConditions.stalenessOf(playerItems.get(0)));

                playerItems = driver.findElements(By.xpath("//ul[@class='items']/li"));
                System.out.println("Número de jugadores después de la paginación: " + playerItems.size());

            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de jugadores.");
                break;
            }
        }
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("http://localhost:4200/matches/1");

        WebElement backButton = driver.findElement(By.xpath("//button[contains(text(), 'Volver')]"));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/leagues"));

        assertTrue(driver.getCurrentUrl().contains("/leagues"), "No redirige correctamente al listado de partidos.");
    }

}

