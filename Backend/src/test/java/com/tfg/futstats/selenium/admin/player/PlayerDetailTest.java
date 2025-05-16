package com.tfg.futstats.selenium.admin.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class PlayerDetailTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("http://localhost:" + this.port + "/players/1");

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
    public void testPlayerInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement playerName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        assertNotNull(playerName, "El nombre del jugador no se muestra correctamente.");

        WebElement playerImage = driver.findElement(By.xpath("//img"));
        assertNotNull(playerImage, "La imagen del jugador no se muestra.");

        WebElement age = driver.findElement(By.xpath("//p[contains(text(), 'Edad:')]"));
        WebElement nationality = driver.findElement(By.xpath("//p[contains(text(), 'Nacionalidad:')]"));
        WebElement position = driver.findElement(By.xpath("//p[contains(text(), 'Posición:')]"));

        assertNotNull(age, "La edad del jugador no se muestra.");
        assertNotNull(nationality, "La nacionalidad del jugador no se muestra.");
        assertNotNull(position, "La posición del jugador no se muestra.");
    }

    @Test
    public void testPlayerStatsDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement shoots = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Tiros:')]")));
        assertNotNull(shoots, "El campo 'Tiros' no está presente.");

        WebElement goals = driver.findElement(By.xpath("//p[contains(text(), 'Goles:')]"));
        WebElement penaltys = driver.findElement(By.xpath("//p[contains(text(), 'Penaltis:')]"));

        assertNotNull(goals, "El campo 'Goles' no está presente.");
        assertNotNull(penaltys, "El campo 'Penales' no está presente.");

        WebElement committedFaults = driver.findElement(By.xpath("//p[contains(text(), 'Faltas cometidas:')]"));
        WebElement recovers = driver.findElement(By.xpath("//p[contains(text(), 'Recuperaciones:')]"));

        assertNotNull(committedFaults, "El campo 'Faltas cometidas' no está presente.");
        assertNotNull(recovers, "El campo 'Recuperaciones' no está presente.");
    }

    @Test
    public void testAdminButtonsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement removeButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Jugador')]")));
        assertNotNull(removeButton, "El boton 'Borrar Jugador' no está presente.");

        WebElement editButton = driver.findElement(By.xpath("//button[contains(text(), 'Editar Jugador')]"));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("http://localhost:4200/players/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement backButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Volver')]")));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();
        System.out.println("Clic en botón 'Volver'.");

        WebElement previousPageHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(previousPageHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testPlayerMatchesPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement firstPlayerMatch = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstPlayerMatch, "No se muestra ningún partido en la lista.");
    }

    @Test
    public void testAddPlayerToFavoritesButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Añadir Jugador a favoritos')]")));

        addButton.click();
    }

    @Test
    public void testEditPlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El botón 'Editar Jugador' no está presente.");

        editButton.click();
        
        System.out.println("Clic en botón 'Editar Jugador'.");

        WebElement editPlayerHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2")));
        assertNotNull(editPlayerHeader, "No se ha redirigido correctamente a la página de edición del jugador.");
    }

    @Test
    public void testDeletePlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Borrar Jugador')]")));
        assertNotNull(deleteButton, "El botón 'Eliminar Jugador' no está presente.");

        deleteButton.click();
    }

}
