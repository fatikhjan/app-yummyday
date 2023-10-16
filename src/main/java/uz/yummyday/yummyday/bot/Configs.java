package uz.yummyday.yummyday.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@Setter
@PropertySource("application.properties")
public class Configs {
    @org.springframework.beans.factory.annotation.Value("${bot.userName}")
    private String username;

    @Value("${bot.token}")
    private String token;
}
