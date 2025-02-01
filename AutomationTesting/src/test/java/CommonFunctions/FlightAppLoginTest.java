package CommonFunctions;

import java.awt.image.DirectColorModel;
import java.sql.Driver;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightAppLoginTest {

	public static void main(String[] args) throws InterruptedException {
/*
 * open browser and maximize
 * delete all cookies
 * enter url
 * add wait time
 * enter email id
 * enter passward
 * click login
 * verify user dashboard
 * if true then send messege login sucessful else login failed
 * if login is sucessful then logout else it shows login error messege
 * close browser
 */
		WebDriver driver=new ChromeDriver(); 
		// open chrome browser
		driver.manage().window().maximize();
		// maximize window
		driver.manage().deleteAllCookies();
		//delete all cookies
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// wait time
		driver.get("https://flights.qedgetech.com/user/utest.html");
		//enter url
		driver.findElement(By.name("email")).sendKeys("interia@email.com");
		//enter email 
		driver.findElement(By.name("password")).sendKeys("Dsop@123");
		//enter password
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		//click sign in
		boolean isDashboardDisplayed=driver.findElement(By.className("profile-userpic")).isDisplayed();
		// check the dashboard is visible
		if(isDashboardDisplayed) {
			System.out.println("Login sucessful");
			driver.findElement(By.xpath("//a[contains(@class,'dropdown-toggle')]")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
			//a[@href='https://flights.qedgetech.com/logout.html']
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://flights.qedgetech.com/logout.html']")));
			driver.findElement(By.xpath("//a[@href='https://flights.qedgetech.com/logout.html']")).click();

//			driver.findElement(By.xpath("//div[@class='dropdown account-menu-dropdown']//a[@class='dropdown-toggle after_login']/i\r\n")).click();
//	click setting to select dropdown
			Thread.sleep(3000);
//			wait time
//			driver.findElement(By.xpath("//a[@href='https://flights.qedgetech.com/logout.html']")).click();
			//logout
			Thread.sleep(3000);
		}else {
			System.out.println("Login failed. Dashboard not found");
		} 
		driver.close();		
		//close browser
	}

}
