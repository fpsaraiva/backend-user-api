//package dev.fpsaraiva.ecommerce_api.infra.docs;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenApiConfig {
//    @Bean
//    public OpenAPI customOpenApi() {
//        return new OpenAPI()
//                .info(new Info().title("E-commerce API")
//                        .description("API for managing shopping users, products and shopping carts")
//                        .version("1.0"))
//                .components(new Components().addSecuritySchemes("Bearer Token",
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")))
//                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
//    }
//}
//
