package com.tfg.futstats.selenium.nonRegisteredUsers.team;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;
import java.util.List;

public class TeamDetailTest extends BaseTest {

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testTeamInfoDisplayed() {
        driver.get("https://localhost:" + this.port + "/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta los elementos del equipo
        WebElement teamName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        WebElement trophies = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Trofeos:')]")));
        WebElement nationality = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Nacionalidad:')]")));
        WebElement trainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Entrenador:')]")));
        WebElement president = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Presidente:')]")));

        // Hacer scroll hasta los elementos y esperar un momento
        scrollToElement(teamName);
        scrollToElement(trophies);
        scrollToElement(nationality);
        scrollToElement(trainer);
        scrollToElement(president);

        assertNotNull(teamName, "El nombre del equipo no se muestra correctamente.");
        assertNotNull(trophies, "El campo 'Trofeos' no está presente.");
        assertNotNull(nationality, "El campo 'Nacionalidad' no está presente.");
        assertNotNull(trainer, "El campo 'Entrenador' no está presente.");
        assertNotNull(president, "El campo 'Presidente' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("https://localhost:" + this.port + "/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de volver
        WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));
        
        // Hacer scroll hasta el elemento y esperar un momento
        scrollToElement(backButton);
        
        // Esperar a que el elemento sea clickeable después del scroll
        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        // Verificar que se ha redirigido correctamente
        WebElement teamsListHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2")));
        scrollToElement(teamsListHeader);
        assertNotNull(teamsListHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testTeamDetailVisibility() {
        driver.get("https://localhost:" + this.port + "/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta los elementos del equipo
        WebElement teamName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2")));
        WebElement teamStats = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Goles')]")));
        
        // Hacer scroll hasta los elementos y esperar un momento
        scrollToElement(teamName);
        scrollToElement(teamStats);
        
        assertNotNull(teamName, "El nombre del equipo no se muestra.");
        assertNotNull(teamStats, "Las estadísticas del equipo no se muestran.");
    }

    @Test
    public void testTeamPlayersPagination() {
        driver.get("https://localhost:" + this.port + "/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta la lista de jugadores
        List<WebElement> playerItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//ul[@class='items']/li")));

        assertTrue(playerItems.size() > 0, "No hay jugadores disponibles para paginar.");

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                    "//pagination-controls//a[contains(@class, 'pagination-next')]")));
                
                // Hacer scroll hasta el botón y esperar un momento
                scrollToElement(nextButton);
                
                // Esperar a que el elemento sea clickeable después del scroll
                wait.until(ExpectedConditions.elementToBeClickable(nextButton));
                
                // Intentar hacer click con JavaScript directamente
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);

                wait.until(ExpectedConditions.stalenessOf(playerItems.get(0)));

                playerItems = driver.findElements(By.xpath("//ul[@class='items']/li"));

            } catch (Exception e) {
                break;
            }
        }
    }
}
