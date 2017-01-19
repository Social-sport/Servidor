package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
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

import modelo.Deporte;
import modelo.Evento;
import modelo.RepositorioDeporte;
import modelo.RepositorioEvento;
import modelo.RepositorioUsuario;
import modelo.Usuario;

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
	public void desuscribirse_evento(){
		login();	
		driver.findElement(By.id("EventsButton")).click();
		driver.findElement(By.id("bSuscribete")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("SuscribeButton")).click();
		driver.findElement(By.id("cerrarSesion")).click();
		
		
	}
	
	@Test
	public void invitar_evento(){
		login();	
		driver.findElement(By.id("EventsButton")).click();
		driver.findElement(By.id("bSuscribete")).click();
		driver.findElement(By.id("SuscribeButton")).click();
		driver.findElement(By.id("eventInvit")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void modificar_evento() throws Exception{
		login();	
		driver.findElement(By.id("perfil")).click();
		driver.findElement(By.id("myEventsButton")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('eventCreated').setAttribute('aria-expanded', 'true')");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement link = driver.findElement(By.id("eventCreated"));
		link.click();
		System.out.println(link.getAttribute("aria-expanded"));
		
		List lista = driver.findElements(By.id("bSuscribete"));
		if (lista.size() == 4){
			WebElement elemento = (WebElement) lista.get(3);
			System.out.println(lista.size());
			System.out.println(elemento.getAttribute("value"));
		} else {
			System.out.println(lista.size());
		}
		
	}
	
	@Test
	public void crear_eventos(){
		RepositorioEvento repoEvento = new RepositorioEvento();
		RepositorioDeporte repoDeporte = new RepositorioDeporte();

		try {
			//Verificar que en la base de datos exista una tabla correspondiente a la creaci�n de eventos.
			assertTrue(repoEvento.checkTableEvent());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		login();
		driver.findElement(By.id("perfil")).click();
		driver.findElement(By.id("creaEvent")).click();
		driver.findElement(By.id("nombre")).sendKeys("Nombre de prueba");
		driver.findElement(By.id("hora")).sendKeys("Hora del evento");
		driver.findElement(By.id("fecha")).sendKeys("11/22/111");
		driver.findElement(By.id("descripcion")).sendKeys("Lorem ipsum dolor sit amet");
		Select dropdown = new Select(driver.findElement(By.id("deporte")));
		dropdown.selectByVisibleText("Baloncesto");
		List<Deporte> list = repoDeporte.listarDeportesUsuario("testgui@server.com");
		boolean encontrado = false;
		for (Deporte dep : list) {
			//Verificar que el usuario este suscrito al deporte sobre el cual desea crear el evento.
			if (dep.getNombre().equals("Baloncesto")) {
				encontrado = true;
				/*WebElement element = driver.findElement(By.id("event-submit"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);*/
				driver.findElement(By.id("event-submit")).click();

			}
		}
		if (!encontrado) { fail(); }
		//Verificar que posterior a la creaci�n del evento, en la base de datos exista el evento creado.
		Evento evento = repoEvento.findEvento("Nombre de prueba");
		if (evento == null) { fail(); }

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
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		try {
			//Verificar que en la base de datos exista una tabla correspondiente a la creaci�n de eventos.
			assertTrue(repoUsuario.checkTableUsuario());
			//Verificar que la tabla "Usuarios" contiene los Atributos: Nombre, Apellido, Email, Fecha nacimiento, nick y Foto.
			assertTrue(repoUsuario.checkColUsuario());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		index();
		driver.findElement(By.id("register-form-link")).click();
		driver.findElement(By.id("username")).sendKeys("test");
		driver.findElement(By.id("emailR")).sendKeys("test@test.test");
		assertNull(repoUsuario.findUsuario("test@test.test"));
		driver.findElement(By.id("contrasenaR")).sendKeys("testtesttest");
		driver.findElement(By.id("nombre")).sendKeys("Test");
		driver.findElement(By.id("apellidos")).sendKeys("Test");
		driver.findElement(By.id("fecha_nacimiento")).sendKeys("16-11-2016");
		driver.findElement(By.id("register-submit")).click();
		//Verificar que posterior al registro se inicie una sesi�n para el usuario registrado.
		index();
		driver.findElement(By.id("login-form-link")).click();
		driver.findElement(By.id("emailL")).sendKeys("test@test.test");
		driver.findElement(By.id("contrasenaL")).sendKeys("testtesttest");
		driver.findElement(By.id("login-submit")).click();
	}
}
