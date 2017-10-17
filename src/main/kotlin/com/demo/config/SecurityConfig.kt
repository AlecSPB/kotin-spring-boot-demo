package com.demo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.StandardPasswordEncoder
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component

import javax.sql.DataSource

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Bean
    fun memoryTokenRepository(): PersistentTokenRepository {
        return InMemoryTokenRepositoryImpl()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return StandardPasswordEncoder(securityProperties.passwordSecret)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .formLogin()
                .loginPage(securityProperties.formLoginLoginPage)
                .and()
                .logout()
                .and()
                .rememberMe()
                .tokenRepository(memoryTokenRepository())
                .tokenValiditySeconds(2419200) // 4 weeks
                .key(securityProperties.rememberMeKey)
                .and()
                .httpBasic()
                .realmName(securityProperties.realmName)
                .and()
                .authorizeRequests()
                .regexMatchers("\\/user\\/create\\/?").permitAll()
                .regexMatchers("\\/user\\/delete\\/.*").hasRole("ADMIN")
                .regexMatchers("\\/user(\\/.*)?(\\?.*)?").authenticated()
                .anyRequest().permitAll()
    }

    @Autowired
    @Throws(Exception::class)
    protected fun globalConfigure(auth: AuthenticationManagerBuilder) {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(securityProperties.usersByUsernameQuery)
                .authoritiesByUsernameQuery(securityProperties.authoritiesByUsernameQuery)
                .passwordEncoder(passwordEncoder())
    }

    @Profile("dev")
    @Component
    @Order(1)
    class H2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

        @Autowired
        private lateinit var console: H2ConsoleProperties

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            // H2 console config
            val path = this.console.path
            val antPattern = if (path.endsWith("/")) path + "**" else path + "/**"
            val h2Console = http.antMatcher(antPattern)
            h2Console.formLogin()
            h2Console.authorizeRequests().anyRequest().hasRole("ADMIN")
            h2Console.csrf().disable()
            h2Console.headers().frameOptions().sameOrigin()
        }
    }

    @Component
    @Order(2)
    class ManagementWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

        @Autowired
        private lateinit var management: ManagementServerProperties

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            val path = management.contextPath
            val antPattern = if (path.endsWith("/")) path + "**" else path + "/**"
            val mngtConsole = http.antMatcher(antPattern)
            mngtConsole.formLogin()
            mngtConsole.authorizeRequests().anyRequest().hasRole("ADMIN")
        }
    }
}
