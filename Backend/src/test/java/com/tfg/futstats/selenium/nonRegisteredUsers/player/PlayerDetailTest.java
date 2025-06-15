package com.tfg.futstats.selenium.nonRegisteredUsers.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class PlayerDetailTest extends BaseTest {

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(1000); // Aumentamos el tiempo de espera
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Esperar a que el elemento sea clickeable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Test
    public void testPlayerInfoDisplayed() {
        driver.get("https://localhost:" + this.port + "/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement playerName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        scrollToElement(playerName);
        assertNotNull(playerName, "El nombre del jugador no se muestra correctamente.");

        WebElement playerImage = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//img")));
        scrollToElement(playerImage);
        assertNotNull(playerImage, "La imagen del jugador no se muestra.");

        WebElement age = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Edad:')]")));
        WebElement nationality = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Nacionalidad:')]")));
        WebElement position = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Posición:')]")));

        scrollToElement(age);
        scrollToElement(nationality);
        scrollToElement(position);

        assertNotNull(age, "La edad del jugador no se muestra.");
        assertNotNull(nationality, "La nacionalidad del jugador no se muestra.");
        assertNotNull(position, "La posición del jugador no se muestra.");
    }

    @Test
    public void testPlayerStatsDisplayed() {
        driver.get("https://localhost:" + this.port + "/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement shoots = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(), 'Tiros:')]")));
        scrollToElement(shoots);
        assertNotNull(shoots, "El campo 'Tiros' no está presente.");

        WebElement goals = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Goles:')]")));
        WebElement penaltys = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Penaltis:')]")));

        scrollToElement(goals);
        scrollToElement(penaltys);

        assertNotNull(goals, "El campo 'Goles' no está presente.");
        assertNotNull(penaltys, "El campo 'Penales' no está presente.");

        WebElement committedFaults = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Faltas cometidas:')]")));
        WebElement recovers = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Recuperaciones:')]")));

        scrollToElement(committedFaults);
        scrollToElement(recovers);

        assertNotNull(committedFaults, "El campo 'Faltas cometidas' no está presente.");
        assertNotNull(recovers, "El campo 'Recuperaciones' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(backButton);
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        try {
            backButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);
        }

        WebElement previousPageHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(previousPageHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testPlayerMatchesPagination() {
        driver.get("https://localhost:" + this.port + "/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement firstPlayerMatch = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@class='items']/li[1]/a")));
        scrollToElement(firstPlayerMatch);
        assertNotNull(firstPlayerMatch, "No se muestra ningún partido en la lista.");
    }
}
