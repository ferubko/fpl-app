package svf.fpl.edu.bot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(value = "com.svf.fpl.edu")
@Import({AdminConfiguration.class, WebSecurityConfig.class})
public class FplBotConfiguration {
}
