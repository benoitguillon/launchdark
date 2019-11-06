package org.talend.launchdark.launchdark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LaunchdarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaunchdarkApplication.class, args);
	}

}
