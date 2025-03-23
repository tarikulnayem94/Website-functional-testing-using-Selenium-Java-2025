package demoQA.com;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)                    // Allows non-static BeforeAll

public class test_IndividualCase {
	WebDriverWait wait;
	WebDriver driver;

	@BeforeAll
	public void BeforeAll() {
		System.out.println("Setup executed before all tests.");
		WebDriverManager.chromedriver().setup();                  // Automatically sets up the driver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
	}
		
	@Test
	@DisplayName("Multiple Window Handling")
	public void WindowMultiple() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		
//		Thread.sleep(2000); 
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
		
		driver.findElement(By.id(("windowButton"))).click();
		String mainWindowHandle = driver.getWindowHandle();           // current active window's handle (ID).
		System.out.println("Main Window GU ID : " + mainWindowHandle);
		
		Set<String> allWindowHandles = driver.getWindowHandles();  // dealing with multiple browser windows or tabs.
		
		Iterator<String> iterator = allWindowHandles.iterator();   // creates an iterator to go through all window handles.                       
		System.out.println(allWindowHandles);
		
	    // while loop is iterating through multiple window handles & switching to the child window.
		
		while (iterator.hasNext()) {                                  //  Check if there is another window
			String ChildWindow = iterator.next();                                          //  Get next window handle
	
			if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {          //  Check if it's NOT the main window
				driver.switchTo().window(ChildWindow);               //  Switch to child window
				String text = driver.findElement(By.id("sampleHeading")).getText();
				Assertions.assertTrue(text.contains("This is a sample page"));  //  verifies the expected string.
				
				System.out.println("Child Window GU ID : " + ChildWindow);
				System.out.println("Child Window URL : " + driver.getCurrentUrl());
			}
		}
		
		driver.close();
		driver.switchTo().window(mainWindowHandle);
	}
	
    @Test
    @DisplayName("Extract First Name & Salary from Web Table")
    void extractNameAndSalary() {
		driver.get("https://demoqa.com/webtables");

		WebElement table = driver.findElement(By.className("rt-tbody"));
		List<WebElement> rows = table.findElements(By.className("rt-tr"));

        System.out.println("First Name | Salary");
        System.out.println("-------------------");

        for (WebElement row : rows) {
            try {
                String firstName = row.findElement(By.xpath(".//div[@class='rt-td'][1]")).getText();
                String salary = row.findElement(By.xpath(".//div[@class='rt-td'][5]")).getText();

                System.out.println(firstName + " | " + salary);              // Print the extracted data               
            } catch (NoSuchElementException e) {
                System.out.println("Skipping empty row...");           // Handle case where row might be empty
            }
        }
        
    }
    
}







