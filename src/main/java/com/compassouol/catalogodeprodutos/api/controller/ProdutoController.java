package com.compassouol.catalogodeprodutos.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import com.compassouol.catalogodeprodutos.api.assembler.ProdutoInputDisassembler;
import com.compassouol.catalogodeprodutos.api.assembler.ProdutoModelAssembler;
import com.compassouol.catalogodeprodutos.api.model.ProdutoModel;
import com.compassouol.catalogodeprodutos.api.model.input.ProdutoInput;
import com.compassouol.catalogodeprodutos.api.openapi.controller.ProdutoControllerOpenApi;
import com.compassouol.catalogodeprodutos.domain.exception.NegocioException;
import com.compassouol.catalogodeprodutos.domain.exception.ProdutoNaoEncontradoException;
import com.compassouol.catalogodeprodutos.domain.model.Produto;
import com.compassouol.catalogodeprodutos.domain.service.CadastroProdutoService;
import com.compassouol.catalogodeprodutos.domain.repository.filter.ProdutoFilter;
import com.compassouol.catalogodeprodutos.domain.repository.ProdutoRepository;
import com.compassouol.catalogodeprodutos.infrastructure.repository.spec.ProdutoSpecs;

import java.util.List;


@RestController
@RequestMapping(value = "/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController implements ProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    private final CadastroProdutoService cadastroProduto;

    private final ProdutoModelAssembler produtoModelAssembler;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    public ProdutoController(CadastroProdutoService cadastroProduto, ProdutoModelAssembler produtoModelAssembler, ProdutoInputDisassembler produtoInputDisassembler) {
        this.cadastroProduto = cadastroProduto;
        this.produtoModelAssembler = produtoModelAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
    }

    @GetMapping
    public List<ProdutoModel> listar() {
        List<Produto> produtos = cadastroProduto.listarTodos();

        return produtoModelAssembler.toCollectionModel(produtos);
    }

    @GetMapping("/search")
    public List<ProdutoModel> pesquisar(ProdutoFilter filtro) {

        List<Produto> produtos = produtoRepository.findAll(ProdutoSpecs.usandoFiltro(filtro));

        return produtoModelAssembler.toCollectionModel(produtos);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long produtoId) {
    	Produto produto = cadastroProduto.buscarOuFalhar(produtoId);

        return produtoModelAssembler.toModel(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@RequestBody @Valid ProdutoInput produtoInput) {        
        try {
        	
        	Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
            produto = cadastroProduto.salvar(produto);

            return produtoModelAssembler.toModel(produto);
            
		} catch (ProdutoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long produtoId,
                                 @RequestBody @Valid ProdutoInput produtoInput) {
        try {
            Produto produtoAtual = cadastroProduto.buscarOuFalhar(produtoId);

            produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

            return produtoModelAssembler.toModel(cadastroProduto.salvar(produtoAtual));
        } catch (ProdutoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    @Override
	@DeleteMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long produtoId) {
        cadastroProduto.buscarOuFalhar(produtoId);
        cadastroProduto.excluir(produtoId);
	}

}
