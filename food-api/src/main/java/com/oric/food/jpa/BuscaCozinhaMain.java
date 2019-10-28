package com.oric.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.oric.food.FoodApiApplication;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);		
		
		Cozinha cozinha = cozinhaRepository.porId(2L);
		
		
			
			System.out.println(cozinha.getNome());
	
		

	}

}
