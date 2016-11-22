package tests;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GUITest {

	private static ChromeDriver driver;
	WebElement element;

	@BeforeClass
	public static void abrirNavegador(){
		System.setProperty("webdriver.chrome.driver", "WebContent\\WEB-INF\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	} 
	
	@AfterClass
	public static void cerrarNavegador(){
		driver.close();
		driver.quit();
	} 

	@Test
	public void login(){
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/signup.html");	
		//driver.findElement(By.xpath(".//*[@id='account']/a")).click();
		driver.findElement(By.id("login-form-link")).click();
		driver.findElement(By.id("username")).sendKeys("test");
		driver.findElement(By.id("contrasena")).sendKeys("test");
		driver.findElement(By.id("login-submit")).click();
		/*try{
			element = driver.findElement (By.xpath(".//*[@id='account_logout']/a"));
		}catch (Exception e){
		}*/
		//Assert.assertNotNull(element);
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}

	@Test
	public void registro() throws InterruptedException{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/signup.html");	
		//driver.findElement(By.xpath(".//*[@id='account']/a")).click();
		driver.findElement(By.id("register-form-link")).click();
		//driver.findElement(By.id("username")).sendKeys("selenium");
		driver.findElement(By.id("apellidos")).sendKeys("selenium");
		driver.findElement(By.id("email")).sendKeys("selenium@selenium");
		//driver.findElement(By.id("contrasena")).sendKeys("selenium");
		driver.findElement(By.id("fecha_nacimiento")).sendKeys("16-11-2016");
		driver.findElement(By.id("register-submit")).click();
		/*try{
			element = driver.findElement (By.xpath(".//*[@id='account_logout']/a"));
		}catch (Exception e){
		}*/
		//Assert.assertNotNull(element);
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
}
