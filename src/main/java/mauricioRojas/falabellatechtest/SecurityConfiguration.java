package mauricioRojas.falabellatechtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Value(value = "${apikey}")
    private String password;

    @Value(value = "${allowed-origin}")
    private String origin;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(origin.equals("all")) {

            http.authorizeRequests().antMatchers("/**").authenticated() // for development porpusoses allow all ips to call the api
            .and().httpBasic().and().csrf().disable();

        } else {

            http.authorizeRequests().antMatchers("/**").hasIpAddress(origin).anyRequest().authenticated() //to allow only conections from "origin" ip, you can set this as you want
            .and().httpBasic().and().csrf().disable();

        }

     
        http.sessionManagement() // dont create a session for this configuration
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }   

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        String encodedPassword = passwordEncoder().encode(password);
        manager.createUser((User.withUsername("falabella").password(encodedPassword))
            .roles("USER").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
