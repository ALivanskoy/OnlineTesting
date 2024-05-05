package sh.alex.onlineTesting.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки UserDetailsService.
 */
@Configuration
public class UserDetailServiceConfig {

    /**
     * Создает и возвращает экземпляр UserDetailsService на основе JDBC.
     *
     * @param passwordEncoder кодировщик паролей
     * @param dataSource      источник данных
     * @return экземпляр UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(@Autowired PasswordEncoder passwordEncoder,
                                                 @Autowired DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
