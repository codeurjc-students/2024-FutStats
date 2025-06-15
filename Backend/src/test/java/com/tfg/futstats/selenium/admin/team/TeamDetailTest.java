package com.tfg.futstats.selenium.admin.team;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;
import java.util.List;

public class TeamDetailTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/teams/1");

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
    public void testTeamInfoDisplayed() {
        testLoginFunctionality();

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
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement backButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));

        scrollToElement(backButton);

        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);

        System.out.println("Clic en botón 'Volver'.");

        WebElement teamsListHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2")));

        scrollToElement(teamsListHeader);
        assertNotNull(teamsListHeader, "No se redirigió correctamente.");

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Editar Equipo')]")));

        scrollToElement(editButton);

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar Equipo' no está presente.");

        editButton.click();

        WebElement editTeamHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2")));

        scrollToElement(editTeamHeader);
        assertNotNull(editTeamHeader, "No se ha redirigido correctamente a la página de edición del equipo.");
    }

    @Test
    public void testDeleteTeamButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Borrar Equipo')]")));

        scrollToElement(deleteButton);

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        assertNotNull(deleteButton, "El botón 'Borrar Equipo' no está presente.");

        deleteButton.click();
        System.out.println("Clic en botón 'Borrar Equipo'.");
    }

    @Test
    public void testAccessPlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement playerMatchLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//ul[@class='items']/li[1]/a")));

        scrollToElement(playerMatchLink);

        wait.until(ExpectedConditions.elementToBeClickable(playerMatchLink));
        assertNotNull(playerMatchLink, "El enlace del 'Player Match' no está presente.");

        playerMatchLink.click();
        System.out.println("Clic en el 'Player Match' (Partido 1).");

        WebElement playerTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2")));

        scrollToElement(playerTitle);
        assertNotNull(playerTitle, "No se ha redirigido correctamente al jugador.");
    }

    @Test
    public void testCreatePlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement createPlayerButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Crear Jugador')]")));

        scrollToElement(createPlayerButton);

        wait.until(ExpectedConditions.elementToBeClickable(createPlayerButton));
        assertNotNull(createPlayerButton, "El botón 'Crear Jugador' no está presente.");

        createPlayerButton.click();

        WebElement createPlayerHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2[contains(text(), 'Nuevo Jugador')]")));

        scrollToElement(createPlayerHeader);
        assertNotNull(createPlayerHeader, "No se ha redirigido correctamente a la página de creación del jugador.");
    }

    @Test
    public void testPointsChartDisplayed() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement pointsChart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pointsChart")));
        assertNotNull(pointsChart, "El gráfico de puntos no se muestra.");
    }

    @Test
    public void testTeamDetailVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement teamName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        scrollToElement(teamName);
        assertNotNull(teamName, "El nombre del equipo no se muestra.");

        WebElement teamStats = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'Goles')]")));
        scrollToElement(teamStats);
        assertNotNull(teamStats, "Las estadísticas del equipo no se muestran.");
    }

    @Test
    public void testTeamPlayersPagination() {
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
                scrollToElement(nextButton);
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
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));

        scrollToElement(goBackButton);

        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);

        assertEquals("https://localhost:" + this.port + "/leagues/1", driver.getCurrentUrl(),
                "No se redirigió correctamente.");
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
