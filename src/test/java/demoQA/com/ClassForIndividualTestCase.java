package demoQA.com;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows non-static BeforeAll

public class ClassForIndividualTestCase {

	WebDriverWait wait;
	WebDriver driver;

	@BeforeAll
	public void BeforeAll() {
		System.out.println("Setup executed before all tests.");
		WebDriverManager.chromedriver().setup(); // Automatically sets up the driver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@Test
	public void Menu() {
//		driver.get("https://demoqa.com/menu");
//		driver.findElement(By.xpath(("//a[normalize-space()='Main Item 1']"))).click();
	}
	
	@Test
	@Tag("move To Element & perform")
    public void testMenuNavigation() throws InterruptedException {
        driver.get("https://demoqa.com/menu");
        Actions actions = new Actions(driver);

        WebElement mainItem2 = driver.findElement(By.xpath("//a[text()='Main Item 2']"));   // Hover over "Main Item 2"
        actions.moveToElement(mainItem2).perform();

        WebElement subSubList = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        actions.moveToElement(subSubList).perform();

        WebElement subSubItem2 = driver.findElement(By.xpath("//a[text()='Sub Sub Item 2']"));
        actions.moveToElement(subSubItem2).click().perform();

        Thread.sleep(2000); 
        System.out.println(driver.getCurrentUrl());
        assertTrue(subSubItem2.isDisplayed());      // Assertion to check expected behavior
    }
}






