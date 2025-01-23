package com.tfg.futstats.selenium.registeredUsers.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class MyProfileTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("http://localhost:4200/leagues");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("user0");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testUserInfoDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement userName = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'admin')]")));
        assertNotNull(userName, "El nombre del usuario no se muestra correctamente.");

    }

    @Test
    public void testLeaguesTeamsPlayersDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement firstLeague = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstLeague, "No se muestra ninguna liga en la lista.");

        WebElement firstTeam = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstTeam, "No se muestra ningún equipo en la lista.");

        WebElement firstPlayer = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstPlayer, "No se muestra ningún jugador en la lista.");
    }

    @Test
    public void testRemoveLeagueButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement removeLeagueButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Liga de favoritos')]")));
        assertNotNull(removeLeagueButton, "El botón 'Borrar Liga de favoritos' no está presente.");

        removeLeagueButton.click();
        System.out.println("Clic en el botón 'Borrar Liga de favoritos'.");
    }

    @Test
    public void testRemoveTeamButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement removeTeamButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Equipo de favoritos')]")));
        assertNotNull(removeTeamButton, "El botón 'Borrar Equipo de favoritos' no está presente.");

        removeTeamButton.click();
        System.out.println("Clic en el botón 'Borrar Equipo de favoritos'.");
    }

    @Test
    public void testRemovePlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement removePlayerButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Jugador de favoritos')]")));
        assertNotNull(removePlayerButton, "El botón 'Borrar Jugador de favoritos' no está presente.");

        removePlayerButton.click();
        System.out.println("Clic en el botón 'Borrar Jugador de favoritos'.");

    }

    @Test
    public void testRemoveUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement removeUserButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar User')]")));
        assertNotNull(removeUserButton, "El botón 'Borrar Usuario' no está presente.");
    }

    @Test
    public void testEditUserButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement editUserButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Usuario')]")));
        assertNotNull(editUserButton, "El botón 'Editar Usuario' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement myProfile = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Mi perfil')]")));
        assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        myProfile.click();

        WebElement backButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Volver')]")));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();
        System.out.println("Clic en botón 'Volver'.");

        WebElement previousPageHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Lista de Jugadores')]")));
        assertNotNull(previousPageHeader, "No se ha redirigido correctamente.");
    }

}
