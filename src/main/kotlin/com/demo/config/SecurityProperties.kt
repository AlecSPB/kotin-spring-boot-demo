package com.demo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.security")
class SecurityProperties {

    var usersByUsernameQuery: String? = null
    var authoritiesByUsernameQuery: String? = null
    var realmName: String? = null
    var rememberMeKey: String? = null
    var formLoginLoginPage: String? = null
    var passwordSecret: String? = null
}
