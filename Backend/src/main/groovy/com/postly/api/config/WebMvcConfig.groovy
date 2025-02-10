package com.postly.api.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env

    @Override
    void addCorsMappings(CorsRegistry reg) {
        List<String> urls = env.getProperty("cors.urls").trim().split(",")

        reg.addMapping("/api/**")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
                .allowCredentials(false).maxAge(3600)

        urls.forEach { url -> reg.addMapping(url) }
    }
}
