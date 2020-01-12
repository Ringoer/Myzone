package xmu.ringoer.myzone.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import xmu.ringoer.myzone.zuul.filters.error.ErrorFilter;
import xmu.ringoer.myzone.zuul.filters.post.ResponseFilter;
import xmu.ringoer.myzone.zuul.filters.pre.LoginFilter;
import xmu.ringoer.myzone.zuul.filters.pre.TokenFilter;

/**
 * @author Ringoer
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

	@Bean
	public LoginFilter loginFilter(){
		return new LoginFilter();
	}

	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}

	@Bean
	public ResponseFilter responseFilter(){
		return new ResponseFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

}
