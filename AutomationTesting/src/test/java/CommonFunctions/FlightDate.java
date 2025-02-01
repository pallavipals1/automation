package CommonFunctions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightDate {

	public static void main(String[] args) throws Throwable {
		/*
		 * locator types primary locators id, name, className, secondary locator
		 * linkText, partialLinkText, tagName (for specfic conditions) advanced locator
		 * xpath
		 */
		/*
		 * open the web browser maximize the browser delete all cookies enter the url
		 * enter the email enter the password click login visibility of dashboard enter
		 * the date take the screen shot logout
		 */
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://flights.qedgetech.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		driver.findElement(By.name("email")).sendKeys("interia@email.com");
		driver.findElement(By.name("password")).sendKeys("Dsop@123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(1000);

//-----------------	Logout Function
//			driver.findElement(By.xpath("//a[@data-toggle='dropdown']")).click();
//			Thread.sleep(2000);
//			driver.findElement(By.linkText("Logout")).click();
//			Thread.sleep(2000);
//			driver.quit();
//------------------------------

		WebElement userDashboard = null;
		WebElement alertObj = null;

		try {
			userDashboard = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='contact-info']/div/div[1]/h2")));
			System.out.println("Dashboard loaded: " + userDashboard.getText());
		} catch (Exception e) {
			try {
				alertObj = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'alert-danger')]")));
				System.out.println("Login failed: " + alertObj.getText());
			} catch (Exception ex) {
				System.out.println("Neither dashboard nor error message found.");
			}
		}

		if (userDashboard != null && userDashboard.isDisplayed()) {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-info\"]/div/div[1]/h2")));
			System.out.println(userDashboard.getText());
			System.out.println(driver.findElement(By.xpath("//*[@id=\"contact-info\"]/div/div[1]/h2")).getText());
			driver.findElement(By.id("search-date")).click();
			String year = driver.findElement(By.className("ui-datepicker-year")).getText();
			String month = driver.findElement(By.className("ui-datepicker-month")).getText();

			while (!year.equalsIgnoreCase("2027")) {
				driver.findElement(By.xpath("//span[contains(@class,'ui-icon-circle-triangle-e')]")).click();
				year = driver.findElement(By.className("ui-datepicker-year")).getText();
			}
			while (!month.equalsIgnoreCase("February")) {
				driver.findElement(By.xpath("//span[contains(@class,'ui-icon-circle-triangle-e')]")).click();
				month = driver.findElement(By.className("ui-datepicker-month")).getText();
			}
			WebElement table = driver.findElement(By.className("ui-datepicker-calendar"));
			WebElement tableBody = table.findElement(By.tagName("tbody"));
			List<WebElement> trows = tableBody.findElements(By.tagName("tr"));
			for (int i = 0; i < trows.size(); i++) {
				List<WebElement> tcols = trows.get(i).findElements(By.tagName("td"));
				for (int j = 0; j < tcols.size(); j++) {
					String date = tcols.get(j).getText();
					if (date.equalsIgnoreCase("15")) {
						tcols.get(j).click();
						break;
					}
				}
			}
			
//----------------------selecting from and to function--------------------------------
			Select fFrom = new Select(driver.findElement(By.xpath("//select[contains(@class,'sf2')]")));
			fFrom.selectByVisibleText("Hyderabad");
			Select fTo = new Select(driver.findElement(By.xpath("//select[contains(@class,'sf3')]")));
			fTo.selectByVisibleText("Kolkatha");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[contains(@class,'btn-search')]")).click();
			Thread.sleep(5000);
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File trg = new File("screenshots/dateFunction.png");
			FileUtils.copyFile(src, trg);
		} else if (alertObj != null && alertObj.isDisplayed()) {
			driver.findElement(By.className("close")).click();
			Thread.sleep(2000);
			driver.quit();
		}

		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"flights-results\"]/div/div/div[2]")));

		WebElement flightSearch = driver.findElement(By.className("flights_table"));
		WebElement tb = flightSearch.findElement(By.tagName("tbody"));
		
		List<WebElement> trList = tb.findElements(By.tagName("tr"));

		boolean airlineFound = false;
		for (WebElement row : trList) {
		    List<WebElement> td = row.findElements(By.tagName("td"));

		    if (td.size() > 1) { 
		        String airlineName = td.get(0).getText(); 
		        if (airlineName.equalsIgnoreCase("Nagendra Airlines")) {
		            System.out.println("Selecting airline: " + airlineName);
		            WebElement selectButton = td.get(td.size() - 1).findElement(By.xpath(".//*[contains(text(), 'Select')]"));
		            selectButton.click();

		            System.out.println("Clicked 'Select' button for: " + airlineName);
		            airlineFound = true;
		            break; // Stop loop after clicking the button for "Soft Airlines"
		        }
		    }
		}

		if (!airlineFound) {
		    System.out.println("Soft Airlines not found in the table.");
		}


		
//--------------------	manually entering date 
//			driver.findElement(By.id("search-date")).sendKeys("02/27/2025");
//----------------------taking screenshot and saving in file and logout and close	
//			TakesScreenshot ts = (TakesScreenshot)driver;
//			File src = ts.getScreenshotAs(OutputType.FILE);
//			File trg = new File("screenshots/userdashboard.png");
//			FileUtils.copyFile(src, trg);
//			driver.findElement(By.xpath("//a[contains(@class,'after_login')]")).click();
//			driver.findElement(By.linkText("Logout")).click();
//			Thread.sleep(Duration.ofSeconds(1));
//			driver.quit();

	}

}
