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


// client code
// RestTemplate restTemplate() throws Exception {
//	    SSLContext sslContext = new SSLContextBuilder()
//	      .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//	      .build();
//	    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//	    HttpClient httpClient = HttpClients.custom()
//	      .setSSLSocketFactory(socketFactory)
//	      .build();
//	    HttpComponentsClientHttpRequestFactory factory = 
//	      new HttpComponentsClientHttpRequestFactory(httpClient);
//	    return new RestTemplate(factory);
//	}
// protected void configure(HttpSecurity http) throws Exception {
//	    http.authorizeRequests()
//	      .antMatchers("/**")
//	      .permitAll();
//	}
// @Test
// public void whenGETanHTTPSResource_thenCorrectResponse() throws Exception {
//     ResponseEntity<String> response = 
//       restTemplate().getForEntity(WELCOME_URL, String.class, Collections.emptyMap());
//  
//     assertEquals("<h1>Welcome to Secured Site</h1>", response.getBody());
//     assertEquals(HttpStatus.OK, response.getStatusCode());
// }
 