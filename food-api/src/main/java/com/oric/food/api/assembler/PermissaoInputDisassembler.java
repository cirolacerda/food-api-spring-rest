package com.oric.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oric.food.api.model.input.PermissaoInput;
import com.oric.food.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
public Permissao toDomainObject(PermissaoInput permissaoInput) {
		
		return modelMapper.map(permissaoInput, Permissao.class);
	}
	
	public void copyToDomainObjetc(PermissaoInput permissaoInput, Permissao permissao) {
		modelMapper.map(permissaoInput, permissao);
	}
	

}
