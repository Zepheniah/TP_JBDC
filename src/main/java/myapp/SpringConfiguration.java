package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des services logiciels déployés par Spring
 */
@Configuration
public class SpringConfiguration {
	
	@Bean
	public String bye() {
		return "Bye.";
	}

	@Bean
	@Qualifier("fileLoggerWithConstructor")
	public ILogger fileLoggerWithConstructor(@Value("${logfile}") String logFile) {
		return new FileLogger(logFile);
	}


	@Autowired
	@Qualifier("fileLoggerWithConstructor") // pour choisir l'implantation
    ILogger fileLoggerWithConstructor;

	@Bean
	@Qualifier("Calculator_File_Log")
	public ICalculator calcul_file(){
		CalculatorWithLog cal = new CalculatorWithLog();
		cal.setLogger(fileLoggerWithConstructor);
		return cal;
	}


}
