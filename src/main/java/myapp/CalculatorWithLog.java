package myapp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("calculator")
public class CalculatorWithLog implements ICalculator {

    private ILogger logger;

    @PostConstruct
    public void start() {
        if (logger == null) {
            throw new IllegalStateException("null logger");
        }
    }

    @PreDestroy
    public void stop() {
    }

    @Override
    public int add(int a, int b) {
        logger.log(String.format("add(%d,%d)", a, b));
        return (a + b);
    }

    public ILogger getLogger() {
        return logger;
    }

    @Autowired
    public void setLogger(ILogger logger) {
        System.out.println(this+" setLogger "+logger);
        this.logger = logger;
    }

}