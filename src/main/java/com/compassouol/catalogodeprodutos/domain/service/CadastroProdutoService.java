package com.compassouol.catalogodeprodutos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compassouol.catalogodeprodutos.domain.exception.ProdutoNaoEncontradoException;

import com.compassouol.catalogodeprodutos.domain.model.Produto;
import com.compassouol.catalogodeprodutos.domain.repository.ProdutoRepository;

import java.util.List;


@Service
public class CadastroProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public CadastroProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Não existe um produto para este ID"));
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> filtrarTodos(Specification<Produto> produtoSpecification) {
        return produtoRepository.findAll();
    }
    
    @Transactional
	public void excluir(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
			produtoRepository.flush();
		} catch (ProdutoNaoEncontradoException e) {
			throw new ProdutoNaoEncontradoException("Não existe um produto para este ID");
		}
	}

}