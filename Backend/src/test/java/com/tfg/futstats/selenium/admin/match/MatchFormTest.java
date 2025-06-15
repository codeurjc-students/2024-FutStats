package com.tfg.futstats.selenium.admin.match;

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

public class MatchFormTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/leagues/1");

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
        
        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    @Test
    public void testLoginFunctionality2() {
        driver.get("https://localhost:"+ this.port + "/matches/1");

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
        
        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    @Test
    public void testMatchFormVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement createMatchButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Crear Partido')]")));
        scrollToElement(createMatchButton);
        assertNotNull(createMatchButton, "El botón 'Crear Partido' no está presente.");

        wait.until(ExpectedConditions.elementToBeClickable(createMatchButton));
        createMatchButton.click();

        WebElement formTitle = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(text(), 'Nuevo Partido')]")));
        scrollToElement(formTitle);
        assertNotNull(formTitle, "El formulario de creación de partido no se muestra correctamente.");
    }

    @Test
    public void testMatchFormButtons() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editMatchButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Editar Partido')]")));
        
        scrollToElement(editMatchButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(editMatchButton));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();

        WebElement editForm = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//ng-component//div")));
        scrollToElement(editForm);
        assertNotNull(editForm, "El formulario de edición del partido no se muestra.");

        WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Cancelar')]")));
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Crear Partido')]")));

        scrollToElement(cancelButton);
        scrollToElement(saveButton);

        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");
        assertNotNull(saveButton, "El botón 'Crear Partido' no está presente.");
    }

    @Test
    public void testMatchEditFormFields() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editMatchButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Editar Partido')]")));
        
        scrollToElement(editMatchButton);
        
        wait.until(ExpectedConditions.elementToBeClickable(editMatchButton));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();

        WebElement editForm = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//ng-component//div")));
        scrollToElement(editForm);
        assertNotNull(editForm, "El formulario de edición del partido no se muestra.");

        WebElement ligaSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[h3[contains(text(), 'Liga:')]]/select")));
        scrollToElement(ligaSelect);
        assertNotNull(ligaSelect, "El selector de liga no está presente.");
        assertTrue(ligaSelect.isEnabled(), "El selector de liga no está habilitado");

        WebElement team1Select = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[h3[contains(text(), 'Equipo Local:')]]/select")));
        scrollToElement(team1Select);
        assertNotNull(team1Select, "El selector de equipo local no está presente.");
        assertTrue(team1Select.isEnabled(), "El selector de equipo local no está habilitado");

        WebElement team2Select = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//label[h3[contains(text(), 'Equipo Visitante:')]]/select")));
        scrollToElement(team2Select);
        assertNotNull(team2Select, "El selector de equipo visitante no está presente.");
        assertTrue(team2Select.isEnabled(), "El selector de equipo visitante no está habilitado");
    }
}
