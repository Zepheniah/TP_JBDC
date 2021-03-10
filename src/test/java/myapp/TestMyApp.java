package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Test Spring services
 */
@SpringBootTest
public class TestMyApp {

	@Autowired
	ApplicationContext context;

	@Autowired
	IHello helloByService;

	@Resource(name = "helloService")
	IHello helloByName;
	
	@Autowired
	String bye;

	@Test
	public void testHelloService() {
		assertTrue(helloByService instanceof HelloService);
		helloByService.hello();
	}

	@Test
	public void testHelloByName() {
		assertEquals(helloByService, helloByName);
	}

	@Test
	public void testHelloByContext() {
		assertEquals(helloByName, context.getBean(IHello.class));
		assertEquals(helloByName, context.getBean("helloService"));
	}

	@Test
	public void testBye() {
		assertEquals(bye, "Bye.");
	}

	@Autowired
	@Qualifier("fileLoggerWithConstructor") // pour choisir l'implantation
	ILogger fileLoggerWithConstructor;
	@Test
	public void testFileLoggerWithConstructor() {
		assertTrue(fileLoggerWithConstructor instanceof FileLogger);
	}

	@Autowired
	@Qualifier("beanFileLogger")
	ILogger beanFile;
	@Test
	public void beanFileLogger(){
		assertTrue( beanFile instanceof BeanFileLogger);
	}

	@Autowired
	ICalculator calculator;

	@Test
	public void testCalculator() {
		var res = calculator.add(10, 20);
		assertEquals(30, res);
		assertTrue(calculator instanceof CalculatorWithLog);
	}


	@Autowired
	@Qualifier("Calculator_File_Log")
	ICalculator cal;
	@Test
	public void testCalculatorFileLogger() {
		var res = cal.add(10, 20);
		assertEquals(30, res);
		assertTrue(cal instanceof CalculatorWithLog);
		System.out.println(((CalculatorWithLog) cal).getLogger());
		assertTrue(((CalculatorWithLog) cal).getLogger() instanceof FileLogger);
	}
}
