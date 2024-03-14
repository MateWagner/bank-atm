package org.example.bank.security

import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenapiConfig {
  @Bean
  fun openApiCustomizer(): OpenApiCustomizer {
    return OpenApiCustomizer { openAPI ->
      openAPI.paths.values.forEach { pathItem ->
        pathItem.readOperations().forEach { operation ->
          operation.addParametersItem(
            Parameter()
              .`in`("header")
              .name("X-ATM")
              .schema(StringSchema()._default("value"))
              .description("ATM custom header value")
          )
        }
      }
    }
  }
}
