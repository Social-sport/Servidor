package tests;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ElementNotVisibleException;

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
	public void index(){
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		System.err.println("ATENCIÓN: TEST PROBADO CON TOMCAT EN LOCALHOST");
		driver.get("http://localhost:8080/Servidor/index.html");	
		driver.findElement(By.className("btn")).click();
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
//
//	@Test
//	public void login(){
//		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
//		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/signup.html");			
//		//driver.findElement(By.xpath(".//*[@id='account']/a")).click();
//		driver.findElement(By.id("login-form-link")).click();
//		driver.findElement(By.id("username")).sendKeys("test");
//		driver.findElement(By.id("contrasena")).sendKeys("test");
//		driver.findElement(By.id("login-submit")).click();
//		/*try{
//			element = driver.findElement (By.xpath(".//*[@id='account_logout']/a"));
//		}catch (Exception e){
//		}*/
//		//Assert.assertNotNull(element);
//		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
//	}

	@Test
	public void login(){
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		System.err.println("ATENCIÓN: TEST PROBADO CON TOMCAT EN LOCALHOST");
		driver.get("http://localhost:8080/Servidor/signup.html");	
		driver.findElement(By.id("login-form-link")).click();
		driver.findElement(By.id("emailL")).sendKeys("test@test.test");
		driver.findElement(By.id("contrasenaL")).sendKeys("test");
		driver.findElement(By.id("login-submit")).click();
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void muroVacio(){
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		System.err.println("ATENCIÓN: TEST PROBADO CON TOMCAT EN LOCALHOST");
		driver.get("http://localhost:8080/Servidor/muro.html");	
		driver.findElement(By.className("navbar-brand")).click();
		driver.findElement(By.id("search")).sendKeys("amigo1");
		driver.findElement(By.id("buttonBuscar")).click();
		driver.findElement(By.id("listSport")).click();
		driver.findElement(By.id("listFriends")).click();
		try{
			driver.findElement(By.className("navbar-toggle")).click();
		}
		catch(ElementNotVisibleException e){
			System.out.println("---------- ERROR ----------");
			System.err.println("Size is too large; Page didn't collide");
			System.err.println("Size: " + driver.manage().window().getSize());
			System.out.println("---------- ERROR ----------");
		}	
		Dimension newSize = new Dimension(700,806);
		driver.manage().window().setSize(newSize);
		driver.findElement(By.className("navbar-toggle")).click();
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
//	@Test
//	public void registro() throws InterruptedException{
//		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
//		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/signup.html");	
//		//driver.findElement(By.xpath(".//*[@id='account']/a")).click();
//		driver.findElement(By.id("register-form-link")).click();
//		//driver.findElement(By.id("username")).sendKeys("selenium");
//		driver.findElement(By.id("apellidos")).sendKeys("selenium");
//		driver.findElement(By.id("email")).sendKeys("selenium@selenium");
//		//driver.findElement(By.id("contrasena")).sendKeys("selenium");
//		driver.findElement(By.id("fecha_nacimiento")).sendKeys("16-11-2016");
//		driver.findElement(By.id("register-submit")).click();
//		/*try{
//			element = driver.findElement (By.xpath(".//*[@id='account_logout']/a"));
//		}catch (Exception e){
//		}*/
//		//Assert.assertNotNull(element);
//		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
//	}
	
	
	
	@Test
	public void registro() throws InterruptedException{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		System.err.println("ATENCIÓN: TEST PROBADO CON TOMCAT EN LOCALHOST");
		driver.get("http://localhost:8080/Servidor/signup.html");	
		driver.findElement(By.id("register-form-link")).click();
		driver.findElement(By.id("username")).sendKeys("test");
		try {
			driver.findElement(By.id("emailR")).sendKeys("test@test.test");
			driver.findElement(By.id("contrasenaR")).sendKeys("testtesttest");
		}
		catch(ElementNotVisibleException e){
			System.out.println("---------- ERROR ----------");
			System.err.println(e.getMessage());
			System.out.println("---------- ERROR ----------");
		}	
		driver.findElement(By.id("apellidos")).sendKeys("test");
		driver.findElement(By.id("fecha_nacimiento")).sendKeys("16-11-2016");
		driver.findElement(By.id("register-submit")).click();
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
}
