package com.tfg.futstats.selenium.admin.team;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class TeamDetailTest extends BaseTest {

    @Test
    public void testTeamInfoDisplayed() {
        driver.get("http://localhost:4200/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement teamName = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        assertNotNull(teamName, "El nombre del equipo no se muestra correctamente.");

        WebElement trophies = driver.findElement(By.xpath("//p[contains(text(), 'Trofeos:')]"));
        WebElement nationality = driver.findElement(By.xpath("//p[contains(text(), 'Nacionalidad:')]"));
        WebElement trainer = driver.findElement(By.xpath("//p[contains(text(), 'Entrenador:')]"));
        WebElement president = driver.findElement(By.xpath("//p[contains(text(), 'Presidente:')]"));

        assertNotNull(trophies, "El campo 'Trofeos' no está presente.");
        assertNotNull(nationality, "El campo 'Nacionalidad' no está presente.");
        assertNotNull(trainer, "El campo 'Entrenador' no está presente.");
        assertNotNull(president, "El campo 'Presidente' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        driver.get("http://localhost:4200/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement backButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Volver')]")));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();
        System.out.println("Clic en botón 'Volver'.");

        WebElement teamsListHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(teamsListHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("http://localhost:4200/teams/1");

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
    public void testAdminButtonsVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement removeButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Equipo')]")));
        assertNotNull(removeButton, "El boton 'Borrar Jugador' no está presente.");
        WebElement editButton = driver.findElement(By.xpath("//button[contains(text(), 'Editar Equipo')]"));
        assertNotNull(editButton, "El boton 'Editar Equipo' no está presente.");

    }

    @Test
    public void testEditTeamButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Verificar que el botón "Editar Equipo" esté presente
        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Equipo')]")));
        assertNotNull(editButton, "El botón 'Editar Equipo' no está presente.");

        // Hacer clic en el botón "Editar Equipo"
        editButton.click();
        System.out.println("Clic en botón 'Editar Equipo'.");

        // Verificar que se redirige a la página de edición del equipo
        WebElement editTeamHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(editTeamHeader, "No se ha redirigido correctamente a la página de edición del equipo.");
    }

    @Test
    public void testDeleteTeamButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Verificar que el botón "Eliminar Equipo" esté presente
        WebElement deleteButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Equipo')]")));
        assertNotNull(deleteButton, "El botón 'Eliminar Equipo' no está presente.");

        // Hacer clic en el botón "Eliminar Equipo"
        deleteButton.click();
        System.out.println("Clic en botón 'Eliminar Equipo'.");

        assertTrue(driver.getCurrentUrl().contains("/leagues/"), "La URL de la liga no es la correcta.");
        System.out.println("Acceso correcto a la liga " + driver.getCurrentUrl());

    }

    @Test
    public void testAccessPlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Verificar que el primer jugador en la lista tenga el botón "Acceder"
        WebElement firstPlayerLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(firstPlayerLink, "El enlace al primer jugador no está presente.");

        // Hacer clic en el enlace para acceder al jugador
        firstPlayerLink.click();
        System.out.println("Clic en el enlace de acceso al jugador.");

        // Verificar que hemos sido redirigidos a la página de detalles del jugador
        WebElement playerDetailHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(playerDetailHeader, "No se ha redirigido correctamente a la página de detalles del jugador.");
    }

    @Test
    public void testCreatePlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Verificar que el botón "Crear Jugador" esté presente
        WebElement createPlayerButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Crear Jugador')]")));
        assertNotNull(createPlayerButton, "El botón 'Crear Jugador' no está presente.");

        // Hacer clic en el botón "Crear Jugador"
        createPlayerButton.click();
        System.out.println("Clic en el botón 'Crear Jugador'.");

        // Verificar que se redirige a la página de creación del jugador
        WebElement createPlayerHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Nuevo Jugador')]")));
        assertNotNull(createPlayerHeader, "No se ha redirigido correctamente a la página de creación del jugador.");
    }

    @Test
    public void testPointsChartDisplayed() {
        driver.get("http://localhost:4200/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement pointsChart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pointsChart")));
        assertNotNull(pointsChart, "El gráfico de puntos no se muestra.");
    }

    @Test
    public void testPlayersPagination() {
        driver.get("http://localhost:4200/teams/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement paginationNextButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//pagination-controls//a[contains(text(), 'Siguiente')]")));
        assertNotNull(paginationNextButton, "El botón 'Siguiente' de la paginación no está presente.");

        paginationNextButton.click();
        System.out.println("Clic en el botón 'Siguiente' de la paginación.");

        WebElement nextPagePlayer = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='items']/li[1]/a")));
        assertNotNull(nextPagePlayer, "No se muestra ningún jugador en la siguiente página.");
    }

    @Test
    public void testAddToFavoritesButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addButton = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Añadir equipo a favoritos')]")));
        assertNotNull(addButton, "El botón 'Añadir Equipo' no está presente.");
    }

}
