package sh.alex.onlineTesting.security;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

/**
 * Конфигурация безопасности Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    /**
     * Конструктор для внедрения зависимости DataSource.
     *
     * @param dataSource источник данных
     */
    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Создание экземпляра PasswordEncoder для кодирования паролей.
     *
     * @return экземпляр PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурация правил безопасности.
     *
     * @param http объект HttpSecurity для настройки
     * @return SecurityFilterChain
     * @throws Exception если возникает исключение при конфигурации
     */
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/registration", "/login").anonymous()
                        .requestMatchers("/logout", "/test/", "/profile", "/qa").authenticated()
                        .requestMatchers("/qa/", "/users/", "/results").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/profile/").hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .defaultSuccessUrl("/profile")
                        .permitAll())
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(logout ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(false)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .build();
    }
}

