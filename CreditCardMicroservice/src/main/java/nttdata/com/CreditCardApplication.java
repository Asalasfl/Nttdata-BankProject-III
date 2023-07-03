package nttdata.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
public class CreditCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditCardApplication.class, args);
    }

    @Bean
    public Docket api3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("groupCreditCard")
                .select()
                .apis(RequestHandlerSelectors.basePackage("nttdata.com"))
                .paths(PathSelectors.any())
                .build();
    }
}