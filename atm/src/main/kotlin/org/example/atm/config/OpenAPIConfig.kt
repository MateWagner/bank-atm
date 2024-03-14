package org.example.atm.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
  name = "basicAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "basic"
)
class OpenAPIConfig {
//  @Bean
//  fun openAPIConfig():OpenAPI=

}
