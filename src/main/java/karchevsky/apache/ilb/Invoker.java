package main.java.karchevsky.apache.ilb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "karchevsky.apache.ilb.config"
})
public class Invoker extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Invoker.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Invoker.class, args);
    }
}
