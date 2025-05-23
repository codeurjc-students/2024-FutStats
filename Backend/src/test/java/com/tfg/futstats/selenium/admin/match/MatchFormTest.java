package com.tfg.futstats.selenium.admin.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class MatchFormTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/leagues/1");

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
        driver.get("https://localhost:4200/matches/1");

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
    public void testMatchFormVisibility() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement createMatchButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Crear Partido')]")));

        createMatchButton.click();

        WebElement formTitle = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Nuevo Partido')]")));
        assertNotNull(formTitle, "El formulario de creación de partido no se muestra correctamente.");
    }

    @Test
    public void testMatchFormFields() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editMatchButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Partido')]")));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();
        System.out.println("Clic en botón 'Editar Partido'.");

        WebElement editForm = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ng-component//div")));
        assertNotNull(editForm, "El formulario de edición del partido no se muestra.");

        WebElement ligaSelect = driver.findElement(By.xpath("//select[@class='ng-untouched ng-pristine ng-valid']"));
        WebElement team1Select = driver
                .findElement(By.xpath("//h3[contains(text(), 'Equipo Local:')]//following-sibling::select"));
        WebElement team2Select = driver
                .findElement(By.xpath("//h3[contains(text(), 'Equipo Visitante:')]//following-sibling::select"));

        assertNotNull(ligaSelect, "El campo 'Liga' no está presente.");
        assertNotNull(team1Select, "El campo 'Equipo Local' no está presente.");
        assertNotNull(team2Select, "El campo 'Equipo Visitante' no está presente.");

        String team1Value = team1Select.getAttribute("ng-reflect-model");
        assertEquals("FC Barcelona", team1Value, "El equipo local no tiene el valor predeterminado correcto.");

        String team2Value = team2Select.getAttribute("ng-reflect-model");
        assertEquals("Real Madrid", team2Value, "El equipo visitante no tiene el valor predeterminado correcto.");
    }

    @Test
    public void testMatchFormButtons() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editMatchButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Partido')]")));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();
        System.out.println("Clic en botón 'Editar Partido'.");

        // Verificar que el botón "Cancelar" esté presente
        WebElement cancelButton = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Cancelar')]")));
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        // Verificar que el botón "Crear Partido" esté presente
        WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(), 'Crear Partido')]"));
        assertNotNull(saveButton, "El botón 'Crear Partido' no está presente.");
    }

    @Test
    public void testMatchFormSelection() {
        testLoginFunctionality2();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement editMatchButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Partido')]")));
        assertNotNull(editMatchButton, "El botón 'Editar Partido' no está presente.");

        editMatchButton.click();
        System.out.println("Clic en botón 'Editar Partido'.");

        WebElement leagueSelect = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='ng-untouched ng-pristine ng-valid']")));
        leagueSelect.click();

        WebElement teamSelect = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[h3[contains(text(), 'Equipo Local:')]]/select")));
        teamSelect.click();

        teamSelect = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Equipo Visitante:')]//following-sibling::select")));
        teamSelect.click();
    }

}
