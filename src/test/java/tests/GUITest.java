package tests;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.ElementNotVisibleException;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con la GUI
 */

public class GUITest {

	private static ChromeDriver driver;
	WebElement element;

	@BeforeClass
	public static void abrirNavegador(){
		System.setProperty("webdriver.chrome.driver", "WebContent\\WEB-INF\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
	} 
	
	@AfterClass
	public static void cerrarNavegador(){
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.close();
		driver.quit();
	} 
	
	@Test
	public void index(){
		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/");	
		driver.findElement(By.id("iniciar")).click();
	}
	


	@Test
	public void login(){
		index();
		driver.findElement(By.id("login-form-link")).click();
		driver.findElement(By.id("emailL")).sendKeys("testgui@server.com");
		driver.findElement(By.id("contrasenaL")).sendKeys("testgui");
		driver.findElement(By.id("login-submit")).click();
	}
	
	@Test
	public void perfil(){
		login();	
		driver.findElement(By.id("perfil")).click();
		driver.findElement(By.id("mySportsButton")).click();
		driver.findElement(By.id("myEventsButton")).click();
		driver.findElement(By.id("mySportsButton")).click();
		driver.findElement(By.id("myEventsButton")).click();
		driver.findElement(By.id("myFriendsButton")).click();
		driver.findElement(By.id("FollowedButton")).click();
		driver.findElement(By.id("myFriendsButton")).click();
		driver.findElement(By.id("creaEvent")).click();
		driver.findElement(By.id("configEdit1")).click();
		driver.findElement(By.id("perfil")).click();
	}
	
	@Test
	public void crear_eventos(){
		login();	
		
		driver.findElement(By.id("perfil")).click();
		
		driver.findElement(By.id("creaEvent")).click();
		driver.findElement(By.id("nombre")).sendKeys("Nombre de prueba");
		driver.findElement(By.id("hora")).sendKeys("Hora del evento");
		driver.findElement(By.id("fecha")).sendKeys("11/22/1111");
		driver.findElement(By.id("descripcion")).sendKeys("Lorem ipsum dolor sit amet, "
				+ "consectetur adipiscing elit. Integer eget lacus et massa vestibulum "
				+ "scelerisque. Nulla ac leo sed orci egestas viverra ac a elit.");
		Select dropdown = new Select(driver.findElement(By.id("deporte")));
		dropdown.selectByVisibleText("Baloncesto");
		driver.findElement(By.id("event-submit")).click();
				
	}
	
	@Test
	public void muroVacio(){
		login();
		driver.findElement(By.className("navbar-brand")).click();
		driver.findElement(By.id("search")).sendKeys("amigo1");
		driver.findElement(By.id("buttonBuscar")).click();
		driver.findElement(By.id("EventsButton")).click();
		driver.findElement(By.id("SportsButton")).click();
		driver.findElement(By.id("NotificationButton")).click();
		driver.findElement(By.id("addFriendsButton")).click();
		
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
		newSize = new Dimension(1100,806);
		driver.manage().window().setSize(newSize);
		driver.findElement(By.id("socialSport")).click();
		driver.findElement(By.id("home"));
		driver.findElement(By.id("perfil")).click();
		driver.findElement(By.id("home"));
		driver.findElement(By.id("cerrarSesion")).click();
	}
	
	
	
	@Test
	public void registro() throws InterruptedException{
		index();
		driver.findElement(By.id("register-form-link")).click();
		driver.findElement(By.id("username")).sendKeys("test");
		driver.findElement(By.id("emailR")).sendKeys("test@test.test");
		driver.findElement(By.id("contrasenaR")).sendKeys("testtesttest");
		driver.findElement(By.id("nombre")).sendKeys("Test");
		driver.findElement(By.id("apellidos")).sendKeys("Test");
		driver.findElement(By.id("fecha_nacimiento")).sendKeys("16-11-2016");
		driver.findElement(By.id("register-submit")).click();
	}
}
