package com.svf.fpl.edu.config;

import com.google.common.cache.CacheBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
//@EnableWebMvc
//@EnableWebSecurity
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.svf.fpl.edu")
@EntityScan(basePackages = "com.svf.fpl.edu")
@ComponentScan(value = "com.svf.fpl.edu")
public class FplClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

//    @Bean
//    public FantasyPremierLeagueClient fplClient() {
//        return new FantasyPremierLeagueClient();
//    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("players", "fullInformation") {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder()
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .build()
                        .asMap(),
                        false);
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOrigins("*")
//                .allowedHeaders("*");
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetailsService userDetailsService = getUserDetails();
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().disable().csrf().disable().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .antMatchers("/api/auth/login").permitAll()
//                .antMatchers("/api/auth/register").permitAll()
//                .antMatchers("/player/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf()
//                .disable().exceptionHandling()
//                .authenticationEntryPoint(unauthorizedEntryPoint()).and()
//                .apply(new JwtConfigurer(jwtTokenProvider));
//        http.cors();
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }

//    @Bean
//    public PasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public AuthenticationEntryPoint unauthorizedEntryPoint() {
//        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
//                "Unauthorized");
//    }
//
//    @Bean
//    public UserDetailsService getUserDetails() {
//        return new CustomUserDetailsService();
//    }
}
