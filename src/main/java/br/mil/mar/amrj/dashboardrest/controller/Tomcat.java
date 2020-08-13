package br.mil.mar.amrj.dashboardrest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/tomcat")
public class Tomcat {

	@GetMapping("list")
	@ResponseJson
	public ResponseEntity<Flux<String>> list() {
		WebClient webClient = WebClient.builder().baseUrl("http://10.1.32.181:8080")
				.defaultHeader(HttpHeaders.USER_AGENT, "Tomcat").build();

		Flux<String> bodyToFlux = webClient.get().uri("/manager/text/list")
				.header("Authorization", "Basic " + Base64Utils.encodeToString(("admin" + ":" + "tomcat").getBytes()))
				.retrieve()
				.bodyToFlux(String.class);

		return ResponseEntity.ok(bodyToFlux);

	}
}
