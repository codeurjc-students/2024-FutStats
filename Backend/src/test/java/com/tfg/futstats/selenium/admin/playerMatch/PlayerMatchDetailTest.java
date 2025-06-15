package com.tfg.futstats.selenium.admin.playerMatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

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

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testLoginFunctionality2() {
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
    public void testPlayerName() {
       testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement playerName = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        assertNotNull(playerName, "El nombre del jugador no se muestra.");
    }

    @Test
    public void testPlayerMatchStats() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement shoots = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Tiros:')]")));
        assertNotNull(shoots, "Los tiros no se muestran.");

        WebElement goals = driver.findElement(By.xpath("//p[contains(text(), 'Goles:')]"));
        assertNotNull(goals, "Los goles no se muestran.");

        WebElement faultsReceived = driver.findElement(By.xpath("//p[contains(text(), 'Faltas recibidas:')]"));
        WebElement offsides = driver.findElement(By.xpath("//p[contains(text(), 'Fueras de juego:')]"));
        assertNotNull(faultsReceived, "Las faltas recibidas no se muestran.");
        assertNotNull(offsides, "Los fueras de juego no se muestran.");

        WebElement passes = driver.findElement(By.xpath("//p[contains(text(), 'Pases:')]"));
        WebElement assists = driver.findElement(By.xpath("//p[contains(text(), 'Asistencias:')]"));
        assertNotNull(passes, "Los pases no se muestran.");
        assertNotNull(assists, "Las asistencias no se muestran.");

        WebElement saves = driver.findElement(By.xpath("//p[contains(text(), 'Paradas:')]"));
        WebElement goalsConceded = driver.findElement(By.xpath("//p[contains(text(), 'Goles concedidos:')]"));
        assertNotNull(saves, "Las paradas no se muestran.");
        assertNotNull(goalsConceded, "Los goles concedidos no se muestran.");
    }

    @Test
    public void testAdminButtonsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement removeButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[contains(text(), 'Borrar Estadística de Jugador')]")));
        assertNotNull(removeButton, "El boton de borrar no se muestra");

        WebElement editButton = driver
                .findElement(By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]"));
        assertNotNull(editButton, "El boton de editar no se muestra");
    }

    @Test
    public void testEditPlayerStats() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        scrollToElement(editButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        try {
            editButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        }
        System.out.println("Clic en botón 'Editar Estadística de Jugador'.");

        WebElement editForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ng-component//div")));
        scrollToElement(editForm);
        assertNotNull(editForm, "El formulario de edición del jugador no se muestra.");

        WebElement shoots = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[contains(text(), 'Tiros:')]")));
        scrollToElement(shoots);
        assertNotNull(shoots, "El campo 'Tiros' no se muestra.");

        WebElement goals = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[contains(text(), 'Goles:')]")));
        scrollToElement(goals);
        assertNotNull(goals, "El campo 'Goles' no se muestra.");

        WebElement penalties = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[contains(text(), 'Penales:')]")));
        scrollToElement(penalties);
        assertNotNull(penalties, "El campo 'Penales' no se muestra.");

        WebElement faultsReceived = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[contains(text(), 'Faltas Recibidas:')]")));
        scrollToElement(faultsReceived);
        assertNotNull(faultsReceived, "El campo 'Faltas Recibidas' no se muestra.");

        WebElement offsides = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[contains(text(), 'Fuera de Juego:')]")));
        scrollToElement(offsides);
        assertNotNull(offsides, "El campo 'Fuera de Juego' no se muestra.");
    }

    @Test
    public void testPlayerMatchDetailVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement playerName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        scrollToElement(playerName);
        assertNotNull(playerName, "El nombre del jugador no se muestra.");

        WebElement matchStats = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(), 'Goles')]")));
        scrollToElement(matchStats);
        assertNotNull(matchStats, "Las estadísticas del partido no se muestran.");
    }

    @Test
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement goBackButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Volver')]")));
        scrollToElement(goBackButton);
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        goBackButton.click();

        assertEquals("https://localhost:" + this.port + "/matches/1", driver.getCurrentUrl(), "No se redirigió correctamente.");
    }

}
