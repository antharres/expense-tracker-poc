package pl.maciejczekp.expense.tracker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pl.maciejczekp.expense.tracker.configuration.security.CsrfHeaderFilter;

@SpringBootApplication
@ComponentScan(basePackages = "pl.maciejczekp.expense.tracker")
public class Application {

    @Bean
    CsrfHeaderFilter csrfHeaderFilter(@Value("${server.contextPath}") String contextPath) {
        return new CsrfHeaderFilter(contextPath);
    }

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}