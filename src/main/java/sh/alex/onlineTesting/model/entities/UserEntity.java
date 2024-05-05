package sh.alex.onlineTesting.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сущность UserEntity представляет пользователя системы и реализует интерфейс UserDetails для аутентификации.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    /**
     * Уникальное имя пользователя.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Пароль пользователя.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Указывает, является ли учетная запись пользователя не просроченной.
     */
    @Column(nullable = false)
    private boolean accountNonExpired;

    /**
     * Указывает, не заблокирована ли учетная запись пользователя.
     */
    @Column(nullable = false)
    private boolean accountNonLocked;

    /**
     * Указывает, не истекли ли учетные данные пользователя.
     */
    @Column(nullable = false)
    private boolean credentialsNonExpired;

    /**
     * Указывает, включена ли учетная запись пользователя.
     */
    @Column(nullable = false)
    private boolean enabled;

    /**
     * Набор полномочий пользователя.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "authority")
    private Set<String> authority;

    /**
     * Конструктор для создания нового пользователя с ролью ROLE_USER.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    public UserEntity(String username, String password) {
        this(username, password, new HashSet<SimpleGrantedAuthority>());
        authority.add("ROLE_USER");
    }

    /**
     * Конструктор для создания нового пользователя с заданными полномочиями.
     *
     * @param username  имя пользователя
     * @param password  пароль пользователя
     * @param authority набор полномочий пользователя
     */
    public UserEntity(String username, String password, Set<SimpleGrantedAuthority> authority) {
        this(username, password, true, true, true, true, authority);
    }

    /**
     * Конструктор для создания нового пользователя с заданными параметрами.
     *
     * @param username              имя пользователя
     * @param password              пароль пользователя
     * @param accountNonExpired     признак непросроченной учетной записи
     * @param accountNonLocked      признак незаблокированной учетной записи
     * @param credentialsNonExpired признак непросроченных учетных данных
     * @param enabled               признак включенной учетной записи
     * @param authority             набор полномочий пользователя
     */
    public UserEntity(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<SimpleGrantedAuthority> authority) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authority = authority.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    /**
     * Переопределение метода toString() для получения строкового представления объекта UserEntity.
     *
     * @return строковое представление объекта UserEntity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("CredentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.authority).append("]");
        return sb.toString();
    }

    /**
     * Получение набора полномочий пользователя.
     *
     * @return набор полномочий пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
