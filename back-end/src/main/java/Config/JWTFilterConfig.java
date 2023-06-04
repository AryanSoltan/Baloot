package Config;


import Filter.JWTFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTFilterConfig
{
    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter();
    }

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JWTFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtFilter());
        registration.addUrlPatterns("/*");
        registration.setName("JWTFilter");
        registration.setOrder(1);
        return registration;
    }
}
