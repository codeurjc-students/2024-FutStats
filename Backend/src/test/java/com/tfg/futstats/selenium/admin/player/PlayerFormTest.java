package com.tfg.futstats.selenium.admin.player;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;
import java.time.Duration;

public class PlayerFormTest extends BaseTest {

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/players/1");

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
    public void testPlayerFormLoaded() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement formTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(formTitle, "El título del formulario 'Nuevo Jugador' no se muestra.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement ageField = driver.findElement(By.xpath("//input[@placeholder='Edad']"));
        WebElement positionField = driver.findElement(By.xpath("//input[@placeholder='Posición']"));
        WebElement nationalityField = driver.findElement(By.xpath("//input[@placeholder='Nacionalidad']"));

        assertNotNull(nameField, "El campo 'Nombre' no está presente.");
        assertNotNull(ageField, "El campo 'Edad' no está presente.");
        assertNotNull(positionField, "El campo 'Posición' no está presente.");
        assertNotNull(nationalityField, "El campo 'Nacionalidad' no está presente.");
    }

    @Test
    public void testPlayerFormFieldsInteraction() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement formTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(formTitle, "El título del formulario 'Nuevo Jugador' no se muestra.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement ageField = driver.findElement(By.xpath("//input[@placeholder='Edad']"));
        WebElement positionField = driver.findElement(By.xpath("//input[@placeholder='Posición']"));
        WebElement nationalityField = driver.findElement(By.xpath("//input[@placeholder='Nacionalidad']"));

        nameField.sendKeys("Vinicius Jr.");
        ageField.sendKeys("22");
        positionField.sendKeys("Delantero");
        nationalityField.sendKeys("Brasil");
    }

    @Test
    public void testCancelButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement cancelButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Cancel')]")));
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        cancelButton.click();
        System.out.println("Clic en botón 'Cancelar'.");

        WebElement playerListHeader = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(playerListHeader, "No se ha redirigido correctamente.");
    }

    @Test
    public void testSaveButtonFunctionality() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement saveButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Save')]")));
        assertNotNull(saveButton, "El botón 'Save' no está presente.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement ageField = driver.findElement(By.xpath("//input[@placeholder='Edad']"));
        WebElement positionField = driver.findElement(By.xpath("//input[@placeholder='Posición']"));
        WebElement nationalityField = driver.findElement(By.xpath("//input[@placeholder='Nacionalidad']"));

        nameField.sendKeys("Vinicius Jr.");
        ageField.sendKeys("22");
        positionField.sendKeys("Delantero");
        nationalityField.sendKeys("Brasil");

        saveButton.click();
        System.out.println("Clic en botón 'Save'.");
    }

    @Test
    public void testRemoveImageOption() {
        testLoginFunctionality();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement editButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Jugador')]")));
        assertNotNull(editButton, "El boton 'Editar Jugador' no está presente.");

        editButton.click();

        WebElement formTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(formTitle, "El título del formulario 'Nuevo Jugador' no se muestra.");

        WebElement removeImageCheckbox = driver.findElement(By.xpath("//input[@name='removeImage']"));
        WebElement imageUploadLabel = driver.findElement(By.xpath("//label[contains(text(), 'Subir imagen')]"));

        if (isEditingPlayer()) {
            assertTrue(removeImageCheckbox.isDisplayed(),
                    "La opción de quitar imagen no está presente en el modo edición.");
            assertTrue(imageUploadLabel.isDisplayed(),
                    "El campo 'Subir imagen' debe estar visible en el modo edición.");
        } else {
            assertFalse(removeImageCheckbox.isDisplayed(),
                    "La opción de quitar imagen no debe estar presente en el modo creación.");
            assertFalse(imageUploadLabel.isDisplayed(),
                    "El campo 'Subir imagen' no debe estar presente en el modo creación.");
        }
    }

    private boolean isEditingPlayer() {
        return driver.findElements(By.xpath("//h2[contains(text(), 'Nuevo Jugador')]")).isEmpty();
    }

}
