package Magento;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Magento {
	
	@Test(priority=2, enabled=false, invocationCount=3, dependsOnMethods="register")
	public void login() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//driver.manage().window().maximize();//this will open the browser window maximized
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // good practice to have this as it will implicitly check the elements for 30 seconds
		driver.get("http://magento.com");// can also use driver.natigate().to("http://magento.com");
		
		//the part is for login, userid, password and continue
		driver.findElement(By.id("gnav_557")).click();
		driver.findElement(By.id("email")).sendKeys("tester99@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("tester99");
		driver.findElement(By.id("send2")).click();
		
		//Thread.sleep(5000);//causes the currently executing thread to sleep for 5 seconds
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div")));
		
		String error = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div")).getText();// Getting text from error message
		System.out.println("Error Displayed: " +error);
		Assert.assertEquals(error, "Invalid login or password.");
		/*if(error.equals("Invalid login or password."))
		{
			System.out.println("Test has passed!");
		}*/
		driver.quit();
		}
	
	@Test(priority=1)
	public void register() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();//instantiating a ChromeDriver class
		//driver.manage().window().maximize();//this will open the browser window maximized
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
		driver.get("http://magento.com");// can also use driver.natigate().to("http://magento.com");
		driver.findElement(By.id("gnav_557")).click();
		driver.findElement(By.id("register")).click();
		driver.findElement(By.id("firstname")).sendKeys("tester");
		driver.findElement(By.id("lastname")).sendKeys("99");
		driver.findElement(By.id("email_address")).sendKeys("tester99@gmail.com");
		driver.findElement(By.id("self_defined_company")).sendKeys("testers inc.");

		//using Select class for selecting value from drop downs
		Select dropdown1 = new Select(driver.findElement(By.id("company_type")));
		dropdown1.selectByIndex(4);
		Select dropdown2 = new Select(driver.findElement(By.id("individual_role")));
		dropdown2.selectByValue("technical/developer");
		Select dropdown3 = new Select(driver.findElement(By.id("country")));
		dropdown3.selectByVisibleText("United States");

		driver.findElement(By.id("password")).sendKeys("Tester@99tester");
		driver.findElement(By.id("password-confirmation")).sendKeys("Tester@99tester");
		//driver.findElement(By.className("recaptcha-checkbox-spinner")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"recaptcha-f979c2ff515d921c34af9bd2aee8ef076b719d03\"]/div/div/iframe")));
		driver.findElement(By.className("recaptcha-checkbox-border")).click();
		driver.switchTo().defaultContent();
		if(!driver.findElement(By.id("agree_terms")).isSelected());
		{
			driver.findElement(By.id("agree_terms")).click();
		}
		driver.quit();
	}
}
