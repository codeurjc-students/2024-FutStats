package com.tfg.futstats.selenium.admin.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class UserListTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues");

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
    public void testUserListDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement userListButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Ver Usuarios')]")));

        userListButton.click();

        // Verificar que la lista de usuarios está visible
        WebElement userListHeader = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Usuarios')]")));
        assertNotNull(userListHeader, "El encabezado 'Usuarios' no está presente.");

        // Verificar que los usuarios están listados
        WebElement firstUser = driver.findElement(By.xpath("//ul[@class='items']/li[1]/a"));
        assertNotNull(firstUser, "No se muestra ningún usuario en la lista.");
    }

    @Test
    public void testUserDetailLink() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement userListButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Ver Usuarios')]")));

        userListButton.click();

        WebElement firstUserLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstUserLink, "El enlace al primer usuario no está presente.");

        firstUserLink.click();
        System.out.println("Clic en el enlace para acceder al detalle del usuario.");

        WebElement userDetailHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(userDetailHeader, "No se ha redirigido correctamente a la página de detalles del usuario.");
    }

    @Test
    public void testCreateUserButtonVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement createUserButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Crear Usuario')]")));
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement userListButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Ver Usuarios')]")));

        userListButton.click();

        WebElement backButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Volver')]")));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();
        System.out.println("Clic en botón 'Volver'.");

        WebElement previousPageHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(previousPageHeader, "No se ha redirigido correctamente.");
    }
}
