package com.tfg.futstats.selenium.nonRegisteredUsers.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.io.File;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class UserFormTest extends BaseTest {

    @Test
    public void testUserFormLoaded() {
        driver.get("http://localhost:4200/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement formTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Nuevo Usuario')]")));
        assertNotNull(formTitle, "El título del formulario 'Nuevo Usuario' no se muestra.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Contraseña']"));

        assertNotNull(nameField, "El campo 'Nombre' no está presente.");
        assertNotNull(passwordField, "El campo 'Contraseña' no está presente.");
    }

    @Test
    public void testUserFormFieldsInteraction() {
        driver.get("http://localhost:4200/users/new");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Contraseña']"));

        nameField.sendKeys("Juan Pérez");
        passwordField.sendKeys("contraseña123");

        assertEquals("Juan Pérez", nameField.getAttribute("value"),
                "El campo 'Nombre' no se ha actualizado correctamente.");
        assertEquals("contraseña123", passwordField.getAttribute("value"),
                "El campo 'Contraseña' no se ha actualizado correctamente.");
    }

    @Test
    public void testCancelButtonFunctionality() {
        driver.get("http://localhost:4200/leagues/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement newUser = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Crear Usuario')]")));
        assertNotNull(newUser, "El boton de mi perfil no se muestra correctamente.");

        newUser.click();

        WebElement cancelButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Cancelar')]")));
        assertNotNull(cancelButton, "El botón 'Cancelar' no está presente.");

        cancelButton.click();
        System.out.println("Clic en botón 'Cancelar'.");

        WebElement userListHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2")));
        assertNotNull(userListHeader, "No se ha redirigido correctamente.");
    }

    @Test
    public void testSaveButtonFunctionality() {
        driver.get("http://localhost:4200/users/new");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement saveButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Guardar')]")));
        assertNotNull(saveButton, "El botón 'Guardar' no está presente.");

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Nombre']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Contraseña']"));

        nameField.sendKeys("Juan Pérez");
        passwordField.sendKeys("contraseña123");

        saveButton.click();
        System.out.println("Clic en botón 'Guardar'.");

    }

    @Test
    public void testImageUploadFunctionality() {
        driver.get("http://localhost:4200/users/new");

        WebElement imageFileInput = driver.findElement(By.xpath("//input[@type='file']"));
        assertNotNull(imageFileInput, "El campo de carga de imagen no está presente.");

        File file = new File("src/main/resources/static/no_image.jpg");
        imageFileInput.sendKeys(file.getAbsolutePath());
        System.out.println("Imagen cargada.");

        WebElement imagePreview = driver.findElement(By.xpath("//img"));
        assertNotNull(imagePreview, "La imagen no se ha cargado correctamente.");
    }

}
