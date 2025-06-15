package com.tfg.futstats.selenium.admin.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.tfg.futstats.selenium.BaseTest;

public class UserListTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/leagues");

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

        usernameField.sendKeys("admin");
        passwordField.sendKeys("pass");
        
        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }

    @Test
    public void testUserListDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userListButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Ver Usuarios')]")));
        scrollToElement(userListButton);
        assertNotNull(userListButton, "El botón 'Ver Usuarios' no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userListButton);

        // Verificar que la lista de usuarios está visible
        WebElement userListHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(), 'Usuarios')]")));
        scrollToElement(userListHeader);
        assertNotNull(userListHeader, "El encabezado 'Usuarios' no está presente.");

        // Verificar que los usuarios están listados
        WebElement firstUser = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//ul[@class='items']/li[1]/a")));
        scrollToElement(firstUser);
        assertNotNull(firstUser, "No se muestra ningún usuario en la lista.");
    }

    @Test
    public void testUserDetailLink() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userListButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Ver Usuarios')]")));
        scrollToElement(userListButton);
        assertNotNull(userListButton, "El botón 'Ver Usuarios' no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userListButton);

        WebElement firstUserLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//ul[@class='items']/li[1]/a")));
        scrollToElement(firstUserLink);
        assertNotNull(firstUserLink, "El enlace al primer usuario no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstUserLink);

        WebElement userDetailHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2")));
        scrollToElement(userDetailHeader);
        assertNotNull(userDetailHeader, "No se ha redirigido correctamente a la página de detalles del usuario.");
    }

    @Test
    public void testCreateUserButtonVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement createUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Crear Usuario')]")));
        scrollToElement(createUserButton);
        assertNotNull(createUserButton, "El botón 'Crear Usuario' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar y hacer scroll hasta el botón de ver usuarios
        WebElement userListButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Ver Usuarios')]")));
        scrollToElement(userListButton);
        assertNotNull(userListButton, "El botón 'Ver Usuarios' no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userListButton);

        // Esperar y hacer scroll hasta el botón de volver
        WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(text(), 'Volver')]")));
        
        // Hacer scroll al botón y esperar a que sea clickeable
        scrollToElement(backButton);
        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        
        // Asegurarse de que el botón está visible en el viewport
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", 
            backButton
        );
        
        // Esperar un momento adicional para asegurar que el scroll se ha completado
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        // Intentar hacer click con JavaScript directamente
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        // Verificar que se ha redirigido correctamente
        WebElement previousPageHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2")));
        scrollToElement(previousPageHeader);
        assertNotNull(previousPageHeader, "No se ha redirigido correctamente.");
    }
}
