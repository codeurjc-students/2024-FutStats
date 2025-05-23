package com.tfg.futstats.selenium.admin.playerMatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class PlayerMatchDetailTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/playerMatch/1");

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
    public void testPlayerNameAndImage() {
       testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement playerName = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        assertNotNull(playerName, "El nombre del jugador no se muestra.");

        // Verificar que la imagen del jugador se muestre correctamente
        WebElement playerImage = driver.findElement(By.xpath("//img"));
        assertNotNull(playerImage, "La imagen del jugador no se muestra.");
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
    public void testBackButtonFunctionality() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement playerMatchLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='items']/li[1]/a"))); 
        assertNotNull(playerMatchLink, "El enlace del 'Player Match' no está presente.");

        playerMatchLink.click();
        System.out.println("Clic en el 'Player Match' (Partido 1).");

        WebElement backButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();
        System.out.println("Clic en botón 'Volver'.");

        WebElement matchTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2")));

        assertNotNull(matchTitle, "No se ha redirigido correctamente al partido 1.");
    }

    @Test
    public void testEditPlayerStats() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Estadística de Jugador')]")));
        assertNotNull(editButton, "El botón 'Editar Estadística de Jugador' no está presente.");

        editButton.click();
        System.out.println("Clic en botón 'Editar Estadística de Jugador'.");

        WebElement editForm = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-component//div")));
        assertNotNull(editForm, "El formulario de edición del jugador no se muestra.");

        WebElement shoots = driver.findElement(By.xpath("//label[contains(text(), 'Tiros:')]"));
        WebElement goals = driver.findElement(By.xpath("//label[contains(text(), 'Goles:')]"));
        WebElement penalties = driver.findElement(By.xpath("//label[contains(text(), 'Penales:')]"));
        WebElement faultsReceived = driver.findElement(By.xpath("//label[contains(text(), 'Faltas Recibidas:')]"));
        WebElement offsides = driver.findElement(By.xpath("//label[contains(text(), 'Fuera de Juego:')]"));

        assertNotNull(shoots, "El campo 'Tiros' no se muestra.");
        assertNotNull(goals, "El campo 'Goles' no se muestra.");
        assertNotNull(penalties, "El campo 'Penales' no se muestra.");
        assertNotNull(faultsReceived, "El campo 'Faltas Recibidas' no se muestra.");
        assertNotNull(offsides, "El campo 'Fuera de Juego' no se muestra.");
    }

}
