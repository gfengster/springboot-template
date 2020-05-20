package net.gfeng.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ChannelSecurityConfigurer.ChannelRequestMatcherRegistry;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ChannelRequestMatcherRegistry registry;
		
		registry = http.requiresChannel().anyRequest().requiresSecure();
		
		//registry = http.requiresChannel().antMatchers("*info").requiresSecure();
		
		System.out.println(registry);
	}
}
