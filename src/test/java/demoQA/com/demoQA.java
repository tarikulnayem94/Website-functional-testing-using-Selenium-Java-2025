package demoQA.com;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)                     // Allows non-static BeforeAll

public class demoQA {

	WebDriverWait wait;
	WebDriver driver;

	@BeforeAll
	public void BeforeAll() {
		System.out.println("Setup executed before all tests.");
		WebDriverManager.chromedriver().setup();                        // Automatically sets up the driver
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test
	@DisplayName("Get Domain Title")
	public void getTitle() {
		driver.get("https://demoqa.com/");
		String expectedTitle = driver.getTitle();
		String actualTitle = "DEMOQA";

		if (expectedTitle == actualTitle) {
			System.out.println("Title pass");
		} else {
			System.out.println("Title fail");
			System.out.println(expectedTitle);
		}
	}

	@Test
	@Tag("Check banner Image")
	public void CheckImage() {
		driver.get("https://demoqa.com/");
		boolean bannerImageExists = driver.findElement(By.className("banner-image")).isDisplayed();                   
		System.out.println(bannerImageExists);
		Assertions.assertTrue(bannerImageExists);
	}

	@Test
	@DisplayName("Fillup Text boxes using my details")
	public void submitInputForm() {
		driver.get("https://demoqa.com/text-box");
		List<WebElement> txtBoxElement = driver.findElements(By.className("form-control"));

		txtBoxElement.get(0).sendKeys("Tarikul Nayem");
		txtBoxElement.get(1).sendKeys("nayeem.softwareqa@gmail.com");
		txtBoxElement.get(2).sendKeys("Dhaka");
		txtBoxElement.get(3).sendKeys("Dhaka");

        driver.findElement(By.cssSelector("#submit")).click();
		System.out.println(driver.getTitle());
		
//		WebElement getText = driver.findElement(By.className("mt-4 row"));
//		System.out.println(getText);	
	}

	@Test
	@DisplayName("Muliple Button Click")
	public void clickOnMultipleButon() {
		driver.get("https://demoqa.com/buttons");
		Actions action = new Actions(driver);
		
		List<WebElement> buttonlist = driver.findElements(By.cssSelector("button"));
		
		action.doubleClick(buttonlist.get(1)).perform();
		String buttonText = driver.findElement(By.id("doubleClickMessage")).getText();
		System.out.println(buttonText);
		Assertions.assertTrue(buttonText.contains("You have done a double click"));
		
		action.contextClick(buttonlist.get(2)).perform();
		String buttonText2 = driver.findElement(By.id("rightClickMessage")).getText();
		System.out.println(buttonText2);
		Assertions.assertTrue(buttonText2.contains("You have done a right click"));
		
		buttonlist.get(3).click();
		String buttonText3 = driver.findElement(By.id("dynamicClickMessage")).getText();
		Assertions.assertTrue(buttonText3.contains("You have done a dynamic click"));
		System.out.println(buttonText3);
	}

	@Test
	@DisplayName("Alert Button Handling")
	public void handleAlerts() throws InterruptedException {
		driver.get("https://demoqa.com/alerts");
		
		driver.findElement(By.id("alertButton")).click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
//      driver.switchTo().alert().dismiss();
		
		driver.findElement(By.id("confirmButton")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.id("confirmResult")).getText();
		Thread.sleep(1000);
		
		driver.findElement(By.id("promtButton")).click();
		driver.switchTo().alert().sendKeys("QA Automation");
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		
		String promtAlert= driver.findElement(By.id("promptResult")).getText();
		System.out.println(promtAlert);
		Assertions.assertTrue(promtAlert.contains("Tarikul Nayeem"));
	}
	
	@Test
	@DisplayName("Date Picker from Keyboard")
	public void selectDatebySendKeys() {
		driver.get("https://demoqa.com/date-picker");
		driver.findElement(By.id("datePickerMonthYearInput")).click();
//		driver.findElement(By.id("datePickerMonthYearInput")).clear();
		
//		Use Keys.chord(Keys.CONTROL, "a") for selecting all text properly.      //   (Keys.CONTROL + "A")
		driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
	    driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("12/08/1995");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
        System.out.println(driver.findElement(By.id("datePickerMonthYearInput")).getText());
	}
	
	@Test
	@DisplayName("Select Current date")
	public void gettingCurrentDateAndPasteInTheEditableField() {
		driver.get("https://demoqa.com/date-picker");
		driver.findElement(By.id("datePickerMonthYearInput")).click();
		
//		Use Keys.chord(Keys.CONTROL, "a") for selecting all text properly.      //   (Keys.CONTROL + "A")
		driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
		
		// Create object of SimpleDateFormat class and decide the format
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		// get current date time with Date()
		Date date = new Date();
		
		// Now format the date
		String currentDate = dateFormat.format(date);
		driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(currentDate, Keys.ENTER);
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
	}

	public void waitForThePresenceOfTheElement(By webElement) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(webElement));
	}

	@Test
	@DisplayName("Select date from Dropdown")
	public void selectingDateFromDatePicker() throws InterruptedException {
		driver.get("https://demoqa.com/date-picker");
		WebElement datePickerTextElement = driver.findElement(By.id("datePickerMonthYearInput"));		
		datePickerTextElement.click(); 
		Thread.sleep(500);
		
//		waitForThePresenceOfTheElement(By.className("react-datepicker__month-select"));
		Select yearFromDatepicker = new Select(driver.findElement(By.className("react-datepicker__year-select")));
		yearFromDatepicker.selectByValue("1994");
		Thread.sleep(500);
		
		Select monthFromDatepicker = new Select(driver.findElement(By.className("react-datepicker__month-select")));
		monthFromDatepicker.selectByValue("11");
		Thread.sleep(1000);
		
//		Select year = new Select(driver.findElement(By.className("react-datepicker__year-select")));
//		year.selectByValue("2022");
		driver.findElement(By.xpath("//div[contains(@aria-label,'Choose Thursday, December 8th, 1994')]")).click();
//	    System.out.println(datePickerTextElement.getText());
	}
	
	@Test
	@DisplayName("Select date & time from Dropdown")
	public void selectingDateFromDropdown() throws InterruptedException {
		driver.get("https://demoqa.com/date-picker");
		WebElement datePickerTextElement = driver.findElement(By.id("dateAndTimePickerInput"));		
		datePickerTextElement.click(); 
		Thread.sleep(500);
		
//		waitForThePresenceOfTheElement(By.className("react-datepicker__month-select"));
		driver.findElement(By.xpath("(//li[normalize-space()='05:00'])[1]")).click();                                          
		Thread.sleep(500);
		
		WebElement datePickerTextElement2 = driver.findElement(By.id("dateAndTimePickerInput"));		
		datePickerTextElement2.click(); 
		Thread.sleep(500);
		
		driver.findElement(By.xpath("//span[@class='react-datepicker__year-read-view--down-arrow']")).click();            
		Thread.sleep(500);
//		driver.findElement(By.cssSelector("react-datepicker__year-dropdown")).click();         
//		driver.findElement(By.xpath("div:nth-child(13)")).click();            
		Thread.sleep(500);
		
//		driver.findElement(By.xpath("//div[@class='react-datepicker__year-option react-datepicker__year-option--selected_year']")).click();            
		Thread.sleep(500);
        
//		driver.findElement(By.className("react-datepicker__month-read-view--selected-month")).click();
		Thread.sleep(500);
	}
	
	@Test
	@DisplayName("Select Dropdown")
	public void selectDropdown() {
		driver.get("https://demoqa.com/select-menu");
		
//		driver.findElement(By.id("withOptGroup")).clear();
//		driver.findElement(By.id("withOptGroup")).sendKeys("Group 2, option 1");
		
		Select color = new Select(driver.findElement(By.id("oldSelectMenu")));
		color.selectByValue("5");
		
		Select cars = new Select(driver.findElement(By.id("cars")));
		if (cars.isMultiple()) {
			cars.selectByValue("audi");
			cars.selectByValue("Opel");
		}
	}
	
	@Test
	@DisplayName("Window handling")
	public void Windows() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");

		String parentGUID = driver.getWindowHandle();
		System.out.println("parent window GUID : " + parentGUID);

		driver.findElement(By.id("tabButton")).click();
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(1000);

		Set<String> allGUID = driver.getWindowHandles();
		
		for (String windowHandle : allGUID) {
			if (!windowHandle.equals(parentGUID)) { // Switch to child window
				driver.switchTo().window(windowHandle);
//				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));  	// Wait until the title is present
//				wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
				System.out.println("Child Window Title: " + driver.getTitle()); 
			}
		}
		driver.switchTo().window(parentGUID);                               		// Switch back to parent window
		System.out.println("Back to Parent Window Title: " + driver.getTitle());

		System.out.println("All G U I D :" + allGUID);
		System.out.println("page title before switching : " + driver.getTitle());
		System.out.println("total window : " + allGUID.size());
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
    
	@Test
	@DisplayName("Handling Frame")
	public void handleIframe() {
		driver.get("https://demoqa.com/frames");
		
		int frameHeight = driver.findElement(By.id("frame1Wrapper")).getSize().height;
		int frameWidth = driver.findElement(By.id("frame1Wrapper")).getSize().width;
	    System.out.println("Frame Height: " + frameHeight);
	    System.out.println("Frame Width: " + frameWidth);
	    
	    int frame1Height = driver.findElement(By.id("frame1")).getSize().height;
		int frame1Width = driver.findElement(By.id("frame1")).getSize().width;
	    System.out.println("Frame Height: " + frame1Height);
	    System.out.println("Frame Width: " + frame1Width);

		driver.switchTo().frame("frame2");
		String text = driver.findElement(By.id("sampleHeading")).getText();
		Assertions.assertTrue(text.contains("This is a sample page"));
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

	@AfterAll
	public void closeDriver() {
		driver.close();
	}

}











