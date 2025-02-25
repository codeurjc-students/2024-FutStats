package com.tfg.futstats.selenium.admin.league;

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
    public void testLoginFunctionality() {
        driver.get("http://frontend:4200/leagues/1");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement myProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfileButton, "No se ha iniciado sesión correctamente");
    }

    @Test
    public void testLeagueInfoDisplayed() {
        driver.get("http://frontend:4200/leagues/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement leagueName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(leagueName, "El nombre de la liga no se muestra.");
    }

    @Test
    public void testAddLeagueToFavoritesButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//button[contains(text(), 'Añadir Liga a favoritos')]")));

        WebElement addFavoriteButton = driver
                .findElement(By.xpath("//button[contains(text(), 'Añadir Liga a favoritos')]"));
        assertNotNull(addFavoriteButton, "El botón 'Añadir Liga a favoritos' no está visible.");

        addFavoriteButton.click();
    }

    @Test
    public void testTeamsPagination() {
        driver.get("http://frontend:4200/leagues/1"); 

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
            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de equipos.");
                break; 
            }
        }
    }

    @Test
    public void testAccessToTeam() {
        driver.get("http://frontend:4200/leagues/1");

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
    public void testCreateTeamButtonForAdmin() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Crear Equipo')]")));

        WebElement createTeamButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Equipo')]"));
        assertNotNull(createTeamButton, "El botón 'Crear Equipo' no está visible para el administrador.");

        createTeamButton.click();

        WebDriverWait waitForTeam = new WebDriverWait(driver, Duration.ofSeconds(1));
        waitForTeam.until(ExpectedConditions.urlContains("/teams/new"));

        assertTrue(driver.getCurrentUrl().contains("/teams/new"), "La URL del equipo no es la correcta.");
        System.out.println("Acceso correcto a la creación del equipo: " + driver.getCurrentUrl());
    }

    @Test
    public void testCreateMatchButtonForAdmin() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Crear Partido')]")));

        WebElement createMatchButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Partido')]"));
        assertNotNull(createMatchButton, "El botón 'Crear Partido' no está visible para el administrador.");

        createMatchButton.click();

        WebDriverWait waitForMatch = new WebDriverWait(driver, Duration.ofSeconds(1));
        waitForMatch.until(ExpectedConditions.urlContains("/matches/new"));

        assertTrue(driver.getCurrentUrl().contains("/matches/new"), "La URL del partido no es la correcta.");
        System.out.println("Acceso correcto a la creacion del partido: " + driver.getCurrentUrl());
    }

    @Test
    public void testAdminOptionsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Liga')]")));

        WebElement deleteLeagueButton = driver.findElement(By.xpath("//button[contains(text(), 'Borrar Liga')]"));
        WebElement editLeagueButton = driver.findElement(By.xpath("//button[contains(text(), 'Editar Liga')]"));
        assertTrue(deleteLeagueButton.isDisplayed(), "El botón 'Borrar Liga' no está visible.");
        assertTrue(editLeagueButton.isDisplayed(), "El botón 'Editar Liga' no está visible.");
    }

    @Test
    public void testGoBackButton() {
        driver.get("http://frontend:4200/leagues/1");

        WebElement goBackButton = driver.findElement(By.xpath("//button[contains(text(), 'Volver')]"));
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        goBackButton.click();

        assertEquals("http://frontend:4200/leagues", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }

    @Test
    public void testMatchesPagination() {
        driver.get("http://frontend:4200/leagues/1");

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
        driver.get("http://frontend:4200/leagues/1");

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
