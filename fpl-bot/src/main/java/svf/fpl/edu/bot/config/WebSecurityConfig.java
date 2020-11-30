package svf.fpl.edu.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import svf.fpl.edu.bot.security.CustomUserDetailsService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST"));
        firewall.setAllowSemicolon(true);
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedPeriod(true);
        return firewall;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/", "/login", "/registration").permitAll()
                .antMatchers("/manager", "/league/**")
                .hasAuthority("ADMIN").anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(authenticationFailureHandler())
                .loginPage("/login")//TODO check it.failureUrl("/error")
                .defaultSuccessUrl("/manager", true)
                .usernameParameter("user_name")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
                .sessionManagement()
                .enableSessionUrlRewriting(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web
                .httpFirewall(allowUrlEncodedSlashHttpFirewall())
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/assets/**", "/dist/**");
    }
}
