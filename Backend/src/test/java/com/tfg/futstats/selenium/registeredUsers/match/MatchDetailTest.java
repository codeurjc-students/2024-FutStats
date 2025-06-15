package com.tfg.futstats.selenium.registeredUsers.match;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;
import java.util.List;

public class MatchDetailTest extends BaseTest {

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
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/matches/1");

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
        
        // Esperar a que el botón sea clickeable después del scroll
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        
        // Intentar hacer click con JavaScript si el click normal falla
        try {
            loginButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Test
    public void testMatchDetailVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement matchName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2")));
        scrollToElement(matchName);
        assertNotNull(matchName, "El nombre del partido no se muestra.");

        WebElement team1Stats = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(), 'Disparos')]")));
        scrollToElement(team1Stats);
        assertNotNull(team1Stats, "Las estadísticas del primer equipo no se muestran.");

        WebElement team2Stats = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(), 'Goles')]")));
        scrollToElement(team2Stats);
        assertNotNull(team2Stats, "Las estadísticas del segundo equipo no se muestran.");
    }

    @Test
    public void testMatchStatistics() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement team1Shoots = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Disparos')]")));
        assertNotNull(team1Shoots, "Los disparos del primer equipo no se muestran.");

        WebElement team1Goals = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Goles:')]")));
        assertNotNull(team1Goals, "Los goles del primer equipo no se muestran.");

        WebElement team2Shoots = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Disparos:')]")));
        assertNotNull(team2Shoots, "Los disparos del segundo equipo no se muestran.");

        WebElement team2Goals = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Goles:')]")));
        assertNotNull(team2Goals, "Los goles del segundo equipo no se muestran.");
    }

    @Test
    public void testPlayerMatchesPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        List<WebElement> playerItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li")));

        assertTrue(playerItems.size() > 0, "No hay jugadores disponibles para paginar.");

        System.out.println("Número inicial de jugadores mostrados: " + playerItems.size());

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//pagination-controls//a[contains(@class, 'pagination-next')]")));
                scrollToElement(nextButton);
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
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", goBackButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);
        
        wait.until(ExpectedConditions.urlContains("/leagues/1"));
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://localhost:" + this.port + "/leagues/1", currentUrl, "No se redirigió correctamente.");
    }

}

