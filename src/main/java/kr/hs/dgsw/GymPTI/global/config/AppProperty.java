package kr.hs.dgsw.GymPTI.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.secret-key")
public class AppProperty {

    private String accessSecretKey;

    private String refreshSecretKey;

}
