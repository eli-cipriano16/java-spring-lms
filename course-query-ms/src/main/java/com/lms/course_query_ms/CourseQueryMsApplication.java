package com.lms.course_query_ms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info =
	@Info(title = "Course Query API", version = "${springdoc.version}", description = "Documentation Course Query Service API v1.0")
)
public class CourseQueryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseQueryMsApplication.class, args);
	}

}
