package com.tfg.futstats.selenium.admin.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tfg.futstats.selenium.BaseTest;

public class UserDetailTest extends BaseTest {
        @Test
        public void testLoginFunctionality() {
                driver.get("http://localhost:4200/leagues");

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
        public void testUserInfoDisplayed() {
                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement myProfile = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Mi perfil')]")));
                assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

                myProfile.click();

                WebElement userName = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//p[contains(text(), 'admin')]")));
                assertNotNull(userName, "El nombre del usuario no se muestra correctamente.");

        }

        @Test
        public void testLeaguesTeamsPlayersDisplayed() {
                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement myProfile = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Mi perfil')]")));
                assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

                myProfile.click();

                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement firstLeague = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[h3[contains(text(),'Ligas')]]")));

                WebElement firstTeam = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[h3[contains(text(),'Equipos')]]")));

                WebElement firstPlayer = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[h3[contains(text(),'Jugadores')]]")));
        }

        // @Test
        // public void testRemoveLeagueButtonFunctionality() {
        //         testLoginFunctionality();

        //         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //         WebElement myProfile = wait
        //                         .until(ExpectedConditions.visibilityOfElementLocated(
        //                                         By.xpath("//button[contains(text(), 'Mi perfil')]")));
        //         assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        //         myProfile.click();

        //         WebElement removeLeagueButton = wait.until(ExpectedConditions
        //                         .visibilityOfElementLocated(
        //                                         By.xpath("//button[contains(text(), 'Borrar Liga de favoritos')]")));
        //         assertNotNull(removeLeagueButton, "El botón 'Borrar Liga de favoritos' no está presente.");

        //         removeLeagueButton.click();
        //         System.out.println("Clic en el botón 'Borrar Liga de favoritos'.");
        // }

        // @Test
        // public void testRemoveTeamButtonFunctionality() {
        // testLoginFunctionality();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // WebElement myProfile = wait
        // .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),
        // 'Mi perfil')]")));
        // assertNotNull(myProfile, "El boton de mi perfil no se muestra
        // correctamente.");

        // myProfile.click();

        // WebElement removeTeamButton = wait.until(ExpectedConditions
        // .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar
        // Equipo de favoritos')]")));
        // assertNotNull(removeTeamButton, "El botón 'Borrar Equipo de favoritos' no
        // está presente.");

        // removeTeamButton.click();
        // System.out.println("Clic en el botón 'Borrar Equipo de favoritos'.");
        // }

        // @Test
        // public void testRemovePlayerButtonFunctionality() {
        //         testLoginFunctionality();

        //         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //         WebElement myProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Mi perfil')]")));
        //         assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

        //         myProfile.click();

        //         WebElement removePlayerButton = wait.until(ExpectedConditions
        //         .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Borrar Jugador de favoritos')]")));
        //         assertNotNull(removePlayerButton, "El botón 'Borrar Jugador de favoritos' no está presente.");

        //         removePlayerButton.click();
        //         System.out.println("Clic en el botón 'Borrar Jugador de favoritos'.");

        // }

        @Test
        public void testRemoveUserButtonFunctionality() {
                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement myProfile = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Mi perfil')]")));
                assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

                myProfile.click();

                WebElement removeUserButton = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Borrar User')]")));
                assertNotNull(removeUserButton, "El botón 'Borrar Usuario' no está presente.");
        }

        @Test
        public void testEditUserButtonFunctionality() {
                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement myProfile = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Mi perfil')]")));
                assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

                myProfile.click();

                WebElement editUserButton = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Editar Usuario')]")));
                assertNotNull(editUserButton, "El botón 'Editar Usuario' no está presente.");
        }

        @Test
        public void testBackButtonFunctionality() {
                testLoginFunctionality();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement myProfile = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Mi perfil')]")));
                assertNotNull(myProfile, "El boton de mi perfil no se muestra correctamente.");

                myProfile.click();

                WebElement backButton = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//button[contains(text(), 'Volver')]")));
                assertNotNull(backButton, "El botón 'Volver' no está presente.");

                backButton.click();
                System.out.println("Clic en botón 'Volver'.");

                WebElement previousPageHeader = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Ligas')]")));
                assertNotNull(previousPageHeader, "No se ha redirigido correctamente.");
        }
}
