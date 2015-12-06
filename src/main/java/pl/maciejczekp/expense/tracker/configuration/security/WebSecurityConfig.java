package pl.maciejczekp.expense.tracker.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import pl.maciejczekp.expense.tracker.model.AppUser;
import pl.maciejczekp.expense.tracker.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CsrfHeaderFilter csrfHeaderFilter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.csrfDisabled}")
    private boolean csrfDisabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/templates/**", "/").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/")
                .and().formLogin().disable();
        configureCsrf(http);
    }

    private HttpSecurity configureCsrf(HttpSecurity http) throws Exception {
        if (csrfDisabled) {
            http.csrf().disable()
                    .headers().frameOptions().disable();
        } else {
            http.addFilterAfter(csrfHeaderFilter, CsrfFilter.class)
                    .csrf().csrfTokenRepository(csrfTokenRepository());
        }
        return http;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new UserDetailsProvider(userRepository))
                .passwordEncoder(passwordEncoder);
    }

    private static class UserDetailsProvider implements UserDetailsService {

        private final UserRepository userRepository;

        private UserDetailsProvider(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Optional<AppUser> userOptional = userRepository.findOneByEmail(email);
            return userOptional
                    .map(u -> new UserDetail(u.getEmail(), u.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(u.getRole().name()))))
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("AppUser with email %s not found", email)));
        }

        private static class UserDetail extends org.springframework.security.core.userdetails.User {

            public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
                super(username, password, authorities);
            }
        }
    }

}