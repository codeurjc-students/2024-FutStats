package com.tfg.futstats.selenium.admin.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/matches/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

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

        List<WebElement> waitPlayerButton = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//button[contains(text(), 'Crear Estadisticas de un jugador')]")));
        assertNotNull(waitPlayerButton, "El botón 'Crear Estadisticas de un jugador' no está presente.");

        WebElement deleteMatchButton = driver.findElement(By.xpath("//button[contains(text(), 'Eliminar Partido')]"));
        assertNotNull(deleteMatchButton, "El botón 'Eliminar Partido' no está presente.");

        WebElement editMatchButton = driver.findElement(By.xpath("//button[contains(text(), 'Editar Partido')]"));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");
    }

    @Test
    public void testEditMatchForm() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editMatchButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Partido')]")));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();
        System.out.println("Clic en botón 'Editar Partido'.");

        WebElement editForm = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-component//div")));
        assertNotNull(editForm, "El formulario de edición del partido no se muestra.");

        WebElement ligaSelect = driver.findElement(By.xpath("//select[@class='ng-untouched ng-pristine ng-valid']"));
        WebElement team1Select = driver
                .findElement(By.xpath("//h3[contains(text(), 'Equipo Local:')]//following-sibling::select"));
        WebElement team2Select = driver
                .findElement(By.xpath("//h3[contains(text(), 'Equipo Visitante:')]//following-sibling::select"));

        assertNotNull(ligaSelect, "El campo 'Liga' no está presente.");
        assertNotNull(team1Select, "El campo 'Equipo Local' no está presente.");
        assertNotNull(team2Select, "El campo 'Equipo Visitante' no está presente.");

        String team1Value = team1Select.getAttribute("ng-reflect-model");
        assertEquals("FC Barcelona", team1Value, "El equipo local no tiene el valor predeterminado correcto.");

        String team2Value = team2Select.getAttribute("ng-reflect-model");
        assertEquals("Real Madrid", team2Value, "El equipo visitante no tiene el valor predeterminado correcto.");
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement backButton = driver.findElement(By.xpath("//button[contains(text(), 'Volver')]"));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/leagues/1"));

        assertTrue(driver.getCurrentUrl().contains("/leagues/1"), "No redirige correctamente al listado de partidos.");
    }

}
