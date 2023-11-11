package nnr.com.CashChangeApp.securite;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableCaching
public class ConfigurationSecuriteCashChangeApplication {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                           .csrf(AbstractHttpConfigurer::disable)
                           .authorizeHttpRequests(
                                   authorize->authorize.requestMatchers(HttpMethod.POST, "/cashChangeApplication/**").permitAll()
                                                       .requestMatchers(HttpMethod.POST, "/cashChangeApplication/activation").permitAll()
                                           .anyRequest().authenticated()).build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public CacheManager cacheManager() {
        // Vous pouvez personnaliser ici votre gestionnaire de cache, par exemple, EhCache, Caffeine, Redis, etc.
        return new CaffeineCacheManager();
    }

 }
