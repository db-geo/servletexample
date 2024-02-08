//package fr.cepi.test;
//
//
//import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
//import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
///**
// * Exemple de test sur le navigateur avec Selnium
// */
//public class TestSelenium {
//     private static WebDriver driver;
//
//    @BeforeAll
//    public static void setupClass() {
//
////        FirefoxDriverManager.firefoxdriver().setup();
//        ChromeDriverManager.getInstance().setup();
//        driver = new ChromeDriver();
////        driver = new FirefoxDriver();
////        driver = new ChromeDriver();
////        driver = new SafariDriver();
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        if (driver != null)
//            driver.quit();
//    }
//
//    @Test
//    public void connectOK() throws Exception {
//        driver.get("http://localhost:8080/Gradle___fr_cepi___servletexample_1_0_SNAPSHOT_war/Login");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("connect")));
//
//        driver.findElement(By.name("login")).sendKeys("user");
//        driver.findElement(By.name("password")).sendKeys("pwd");
//        driver.findElement(By.name("connect")).click();
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-danger")));
//        Assertions.assertEquals("Combinaison incorrecte.", driver.findElement(By.cssSelector("div.alert-danger")).getText());
//
//        Assertions.assertEquals("Accueil", driver.getTitle());
//    }
//}
