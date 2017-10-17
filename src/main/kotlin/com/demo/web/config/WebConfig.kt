package com.demo.web.config

import com.demo.web.conversion.DateFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebConfig : WebMvcConfigurerAdapter() {
    override fun addViewControllers(registry: ViewControllerRegistry?) {
        registry!!.addViewController("/login").setViewName("login")
    }

    // If no handler mapping is found, use container's default servlet (which is used to
    // load static resources)
    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer?) {
        configurer!!.enable()
    }

    override fun configurePathMatch(configurer: PathMatchConfigurer?) {
        configurer!!.isUseTrailingSlashMatch = true
    }

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("WEB-INF/i18n/messages")
        messageSource.setCacheSeconds(1)
        return messageSource
    }

    @Bean
    fun dateFormatter(): DateFormatter {
        return DateFormatter()
    }

    override fun addFormatters(registry: FormatterRegistry?) {
        super.addFormatters(registry)
        registry!!.addFormatter(dateFormatter())
    }
}