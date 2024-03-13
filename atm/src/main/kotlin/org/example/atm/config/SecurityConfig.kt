package org.example.atm.config

import org.example.atm.service.CustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customerService: CustomerService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
       http {
           csrf{ disable()}

           authorizeHttpRequests {
               authorize("/api/v1/admin/**", hasAuthority(Roles.ADMIN.name))
               authorize("/api/**",authenticated )
               authorize(anyRequest, permitAll)
           }
           formLogin { }
           httpBasic { }
       }
        return http.build()
    }

    @Bean
    fun userDetailService(): UserDetailsService =
        UserDetailsService(customerService::findByUsername)
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        NoOpPasswordEncoder.getInstance()

}
