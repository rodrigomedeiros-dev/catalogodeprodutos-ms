package com.compassouol.catalogodeprodutos.api.openapi.controller;

import com.compassouol.catalogodeprodutos.api.exceptionhandler.Problem;
import com.compassouol.catalogodeprodutos.api.model.ProdutoModel;
import com.compassouol.catalogodeprodutos.api.model.input.ProdutoInput;

import com.compassouol.catalogodeprodutos.domain.repository.filter.ProdutoFilter;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Produto")
public interface ProdutoControllerOpenApi {

	@ApiOperation("Pesquisa produtos usando filtros. Ex: ?q=termo&min_price=250.50&max_price=750.47")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Preço mínimo do produto. Ex: ?min_price=250.50",
					name = "min_price", paramType = "query", type = "string"),
			@ApiImplicitParam(value = "Preço máximo do produto. Ex: ?max_price=750.47",
					name = "max_price", paramType = "query", type = "string"),
			@ApiImplicitParam(value = "Nome ou descrição do produto. Ex: ?q=termo",
					name = "q", paramType = "query", type = "string"),
	})
	@ApiResponses({
			@ApiResponse(code = 404, message = "Nenhum produto encontrado", response = Problem.class),
			@ApiResponse(code = 200, message = "Produto(s) encontrado(s)")
	})
	List<ProdutoModel> pesquisar(ProdutoFilter filtro);


	@ApiOperation("Busca um produto por ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Nenhuma produto com este ID", response = Problem.class),
            @ApiResponse(code = 200, message = "Produto encontrado")
    })
    ProdutoModel buscar(
            @ApiParam(value = "ID do produto deve ser maior que zero. Ex: 123", example = "123", required = true)
                    Long produtoId);

    @ApiOperation("Cadastra um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
    })
    ProdutoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
                    ProdutoInput produtoInput);
    
    @ApiOperation("Atualiza um produto por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Produto atualizado"),
		@ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
	})
    ProdutoModel atualizar(
			@ApiParam(value = "ID de um produto", example = "1", required = true)
			Long produtoId,
			
			@ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", 
				required = true)
			ProdutoInput produtoInput);
    
    @ApiOperation("Exclui um produto por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Produto excluído"),
		@ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um produto", example = "1", required = true)
			Long produtoId);
}