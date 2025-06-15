package com.tfg.futstats.selenium.admin.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/matches/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        scrollToElement(usernameField);
        scrollToElement(passwordField);
        scrollToElement(loginButton);

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testMatchDetailVisibility() {
        testLoginFunctionality();

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
        testLoginFunctionality();

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
    public void testAdminButtonsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement createPlayerStatsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Crear Estadisticas de un jugador')]")));
        scrollToElement(createPlayerStatsButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(createPlayerStatsButton));
        assertNotNull(createPlayerStatsButton, "El botón 'Crear Estadisticas de un jugador' no está presente.");

        WebElement deleteMatchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Eliminar Partido')]")));
        scrollToElement(deleteMatchButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(deleteMatchButton));
        assertNotNull(deleteMatchButton, "El botón 'Eliminar Partido' no está presente.");

        WebElement editMatchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Partido')]")));
        scrollToElement(editMatchButton);

        wait.until(ExpectedConditions.elementToBeClickable(editMatchButton));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");
    }

    @Test
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));
        
        scrollToElement(goBackButton);
        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);
        
        wait.until(ExpectedConditions.urlContains("/leagues/1"));
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://localhost:" + this.port + "/leagues/1", currentUrl, "No se redirigió correctamente.");
    }

}
