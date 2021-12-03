package com.dongok.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class HelloApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(HelloApplication.class, args);

	}

}
