package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Wait;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

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
		//System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
	} 
	
	@AfterClass
	public static void cerrarNavegador(){
		//System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.close();
		driver.quit();
	} 
	
	@Test
	public void index(){
		driver.get("http://pruebaopenshift-socialsport.rhcloud.com/Servidor/");	
		WebElement myDynamicElement2 = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("iniciar")));
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
		driver.findElement(By.id("cerrarSesion")).click();
	}
	
	@Test
	public void ver_eventos_perfil(){
		login();	
		driver.findElement(By.id("EventsButton")).click();
		WebElement myDynamicElement2 = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("EventsButton")));
	}
	
	//@Test
	public void unirse_evento(){
		login();	
		driver.findElement(By.id("EventsButton")).click();
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("bSuscribete")));
		driver.findElement(By.id("bSuscribete")).click();
		WebElement myDynamicElement2 = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("SuscribeButton")));
		driver.findElement(By.id("SuscribeButton")).click();
		driver.findElement(By.id("cerrarSesion")).click();
	}
	
	@Test
	public void modificar_evento(){
		login();	
		driver.findElement(By.id("perfil")).click();
		driver.findElement(By.id("myEventsButton")).click();
		
		
		
		// 
		//System.out.println(link.getText());
		//link.sendKeys(Keys.TAB);
		//System.out.println(link.getClass());
		//System.out.println(link.getTagName());

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('eventCreated').setAttribute('aria-expanded', 'true')");
		//System.out.println(link.isSelected());
		//System.out.println(link.isDisplayed());
		//System.out.println(link.isEnabled());
		
		//link.getCssValue(arg0);
		//link.click();
		//link.sendKeys(Keys.ENTER);
		WebElement link = driver.findElement(By.id("eventCreated"));
		
		System.out.println(link.getAttribute("aria-expanded"));
		
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("bSuscribete")));
		//driver.findElement(By.cssSelector("a[id*='eventCreated']")).click();
		//driver.findElement(By.id("eventCreated")).click();
		//WebElement myDynamicElement2 = (new WebDriverWait(driver, 10))
				  //.until(ExpectedConditions.presenceOfElementLocated(By.id("bSuscribete")));
		List lista = driver.findElements(By.id("bSuscribete"));
		WebElement elemento = (WebElement) lista.get(0);

		System.out.println(lista.size());
		System.out.println(elemento.getAttribute("value"));
		WebElement elemento2 = (WebElement) lista.get(1);
		System.out.println(elemento2.getAttribute("value"));
		WebElement elemento3 = (WebElement) lista.get(1);
		System.out.println(elemento3.getAttribute("value"));
		/*driver.findElement(By.id("eventEdit")).click();
		driver.findElement(By.id("nombre")).sendKeys("Evento MODIFICADO");
		driver.findElement(By.id("event-submit")).click();
		driver.findElement(By.id("home")).click();*/
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
		
		try {
			driver.findElement(By.className("navbar-toggle")).click();
		}
		catch (ElementNotVisibleException e) {
			System.out.println("Size is too large; Page didn't collide");
			System.out.println("Size: " + driver.manage().window().getSize());
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
