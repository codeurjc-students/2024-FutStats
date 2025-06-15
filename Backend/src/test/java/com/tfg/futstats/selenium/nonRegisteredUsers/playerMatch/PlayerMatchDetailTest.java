package com.tfg.futstats.selenium.nonRegisteredUsers.playerMatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class PlayerMatchDetailTest extends BaseTest {

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
    public void testPlayerName() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement playerName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1")));
        scrollToElement(playerName);
        assertNotNull(playerName, "El nombre del jugador no se muestra.");
    }

    @Test
    public void testPlayerMatchStats() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement shoots = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Tiros:')]")));
        scrollToElement(shoots);
        assertNotNull(shoots, "Los tiros no se muestran.");

        WebElement goals = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Goles:')]")));
        scrollToElement(goals);
        assertNotNull(goals, "Los goles no se muestran.");

        WebElement faultsReceived = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Faltas recibidas:')]")));
        WebElement offsides = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Fueras de juego:')]")));
        scrollToElement(faultsReceived);
        scrollToElement(offsides);
        assertNotNull(faultsReceived, "Las faltas recibidas no se muestran.");
        assertNotNull(offsides, "Los fueras de juego no se muestran.");

        WebElement passes = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Pases:')]")));
        WebElement assists = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Asistencias:')]")));
        scrollToElement(passes);
        scrollToElement(assists);
        assertNotNull(passes, "Los pases no se muestran.");
        assertNotNull(assists, "Las asistencias no se muestran.");

        WebElement saves = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Paradas:')]")));
        WebElement goalsConceded = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[contains(text(), 'Goles concedidos:')]")));
        scrollToElement(saves);
        scrollToElement(goalsConceded);
        assertNotNull(saves, "Las paradas no se muestran.");
        assertNotNull(goalsConceded, "Los goles concedidos no se muestran.");
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(backButton);
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        WebElement matchTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(matchTitle);
        assertNotNull(matchTitle, "No se ha redirigido correctamente al partido 1.");
    }
}
