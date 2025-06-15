package com.tfg.futstats.selenium.admin.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class PlayerDetailTest extends BaseTest {

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
        driver.get("https://localhost:" + this.port + "/players/1");

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
                ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Jugador')]")));
        assertNotNull(removeButton, "El boton 'Borrar Jugador' no está presente.");

        WebElement editButton = driver.findElement(By.xpath("//button[contains(text(), 'Editar Jugador')]"));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");
    }

    @Test
    public void testBackButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Volver')]")));

        scrollToElement(backButton);

        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        assertNotNull(backButton, "El botón 'Volver' no está presente.");

        backButton.click();

        WebElement previousPageHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(previousPageHeader, "No se redirigió correctamente.");
    }

    @Test
    public void testPlayerMatchesPagination() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        List<WebElement> matchItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//ul[@class='items']/li")));

        assertTrue(matchItems.size() > 0, "No hay partidos disponibles para paginar.");

        while (true) {
            try {
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//pagination-controls//a[contains(@class, 'pagination-next')]")));
                scrollToElement(nextButton);
                nextButton.click();
                System.out.println("Clic en botón 'Siguiente' de partidos.");

                wait.until(ExpectedConditions.stalenessOf(matchItems.get(0)));

                matchItems = driver.findElements(By.xpath("//ul[@class='items']/li"));
                System.out.println("Número de partidos después de la paginación: " + matchItems.size());

            } catch (Exception e) {
                System.out.println("Botón 'Siguiente' no está disponible. Fin de la paginación de partidos.");
                break;
            }
        }
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

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));

        scrollToElement(editButton);

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement editPlayerHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2")));
        assertNotNull(editPlayerHeader, "No se ha redirigido correctamente a la página de edición del jugador.");
    }

    @Test
    public void testDeletePlayerButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Borrar Jugador')]")));

        scrollToElement(deleteButton);

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        assertNotNull(deleteButton, "El botón 'Borrar Jugador' no está presente.");

        deleteButton.click();

        System.out.println("Clic en botón 'Borrar Jugador'.");
    }

    @Test
    public void testPlayerDetailVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement playerName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));

        scrollToElement(playerName);

        wait.until(ExpectedConditions.elementToBeClickable(playerName));
        assertNotNull(playerName, "El nombre del jugador no se muestra.");

        WebElement playerStats = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'Goles')]")));

        scrollToElement(playerStats);

        wait.until(ExpectedConditions.elementToBeClickable(playerStats));
        assertNotNull(playerStats, "Las estadísticas del jugador no se muestran.");
    }

    @Test
    public void testGoBackButton() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement goBackButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Volver')]")));

        scrollToElement(goBackButton);

        wait.until(ExpectedConditions.elementToBeClickable(goBackButton));
        assertNotNull(goBackButton, "El botón 'Volver' no está presente.");

        goBackButton.click();

        wait.until(ExpectedConditions.urlToBe("https://localhost:" + this.port + "/teams/1"));

        assertEquals("https://localhost:" + this.port + "/teams/1", driver.getCurrentUrl(),
                "No se redirigió correctamente.");
    }

    @Test
    public void testEditPlayerForm() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement editPlayerButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar Jugador')]")));

        scrollToElement(editPlayerButton);

        wait.until(ExpectedConditions.elementToBeClickable(editPlayerButton));
        assertNotNull(editPlayerButton, "El botón 'Editar Jugador' no está presente.");

        editPlayerButton.click();

        WebElement editForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ng-component//div")));

        scrollToElement(editForm);

        assertNotNull(editForm, "El formulario de edición del jugador no se muestra.");
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Nombre']")));

        scrollToElement(nameInput);

        assertNotNull(nameInput, "El campo 'Nombre' no está presente.");
        WebElement ageInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Edad']")));

        scrollToElement(ageInput);

        assertNotNull(ageInput, "El campo 'Edad' no está presente.");
        WebElement positionInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Posición']")));

        scrollToElement(positionInput);

        assertNotNull(positionInput, "El campo 'Posición' no está presente.");
        WebElement nationalityInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Nacionalidad']")));

        scrollToElement(nationalityInput);

        assertNotNull(nationalityInput, "El campo 'Nacionalidad' no está presente.");
        WebElement teamSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//select[@name='teamName']")));

        scrollToElement(teamSelect);

        assertNotNull(teamSelect, "El campo 'Equipo' no está presente.");
    }

    @Test
    public void testPlayerFormButtons() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Editar')]")));

        scrollToElement(editButton);

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        assertNotNull(editButton, "El botón 'Editar' no está presente.");

        editButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));

        WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(text(), 'Cancel')]")));

        scrollToElement(cancelButton);

        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        assertNotNull(cancelButton, "El botón 'Cancel' no está presente.");
        
        cancelButton.click();

        WebElement playerName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        assertNotNull(playerName, "No se ha redirigido correctamente a la página de detalles del jugador.");
    }

}
