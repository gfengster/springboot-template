package net.gfeng.control.async;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
class RequestAsyncPoolConfig extends WebMvcConfigurerAdapter {

	@Resource
	private ThreadPoolTaskExecutor myThreadPoolTaskExecutor;

	@Override
	public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
		// 处理 callable超时
		configurer.setDefaultTimeout(60 * 1000);
		configurer.setTaskExecutor(myThreadPoolTaskExecutor);
		configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
	}

	@Bean
	public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
		return new TimeoutCallableProcessingInterceptor();
	}
}