package com.tfg.futstats.selenium.registeredUsers.team;

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
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Iniciar Sesión')]")));

        scrollToElement(usernameField);
        scrollToElement(passwordField);
        scrollToElement(loginButton);

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("user0");
        passwordField.sendKeys("pasS123");
        
        loginButton.click();
    }

    @Test
    public void testTeamInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement teamName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        WebElement trophies = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Trofeos:')]")));
        WebElement nationality = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Nacionalidad:')]")));
        WebElement trainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Entrenador:')]")));
        WebElement president = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Presidente:')]")));

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
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));
        
        scrollToElement(backButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        WebElement teamsListHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2")));
        scrollToElement(teamsListHeader);
        assertNotNull(teamsListHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testTeamDetailVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement teamName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2")));
        WebElement teamStats = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Goles')]")));
        
        scrollToElement(teamName);
        scrollToElement(teamStats);
        
        assertNotNull(teamName, "El nombre del equipo no se muestra.");
        assertNotNull(teamStats, "Las estadísticas del equipo no se muestran.");
    }

    @Test
    public void testTeamPlayersPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> playerItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//ul[@class='items']/li")));

        assertTrue(playerItems.size() > 0, "No hay jugadores disponibles para paginar.");

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                    "//pagination-controls//a[contains(@class, 'pagination-next')]")));
                
                scrollToElement(nextButton);
                
                wait.until(ExpectedConditions.elementToBeClickable(nextButton));
                
                nextButton.click();

                wait.until(ExpectedConditions.stalenessOf(playerItems.get(0)));

                playerItems = driver.findElements(By.xpath("//ul[@class='items']/li"));

            } catch (Exception e) {
                break;
            }
        }
    }

    @Test
    public void testAddToFavoritesButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement addButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Añadir equipo a favoritos')]")));
        
        scrollToElement(addButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        assertNotNull(addButton, "El botón 'Añadir Equipo' no está presente.");

        addButton.click();
    }
}
