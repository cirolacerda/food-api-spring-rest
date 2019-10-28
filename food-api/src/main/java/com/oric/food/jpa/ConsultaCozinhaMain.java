package com.oric.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.oric.food.FoodApiApplication;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);		
		
		List<Cozinha> cozinhas = cozinhaRepository.todas();
		
		
		for (Cozinha cozinha : cozinhas) {
			
			System.out.println(cozinha.getNome());
		}
		
		

	}

}
