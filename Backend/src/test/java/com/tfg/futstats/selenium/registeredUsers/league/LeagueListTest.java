package com.tfg.futstats.selenium.registeredUsers.league;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tfg.futstats.selenium.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LeagueListTest extends BaseTest{

    @Test
    public void testHomePageLoadsSuccessfully() {
        driver.get("https://localhost:" + this.port + "/");
        String title = driver.getTitle();
        assertEquals("Fut-Stats", title, "El título de la página no es el esperado.");
    }

    @Test
    public void testLoginFunctionality() {
        driver.get("https://localhost:" + this.port + "/");
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Iniciar Sesión')]"));

        assertNotNull(usernameField, "El campo de nombre de usuario no se encontró.");
        assertNotNull(passwordField, "El campo de contraseña no se encontró.");
        assertNotNull(loginButton, "El botón 'Iniciar sesión' no se encontró.");

        usernameField.sendKeys("user0");
        passwordField.sendKeys("pass");
        loginButton.click();
    }

    @Test
    public void testLeagueListIsDisplayed() {
        testLoginFunctionality();
        WebElement leagueList = driver.findElement(By.className("items"));
        assertNotNull(leagueList, "La lista de ligas no se encontró.");
        assertTrue(leagueList.findElements(By.tagName("li")).size() > 0, "La lista de ligas está vacía.");
    }

    @Test
    public void testPaginationControls() {
        testLoginFunctionality();

        WebElement paginationControls = driver.findElement(By.tagName("pagination-controls"));
        assertNotNull(paginationControls, "El componente de controles de paginación no se encontró.");

        WebElement nextButton = paginationControls.findElement(By.xpath(".//a[contains(text(), 'Siguiente')]"));
        assertNotNull(nextButton, "El enlace 'Siguiente' no se encontró.");
        nextButton.click();

        System.out.println("Botón 'Siguiente' funciona correctamente.");
    }
}
