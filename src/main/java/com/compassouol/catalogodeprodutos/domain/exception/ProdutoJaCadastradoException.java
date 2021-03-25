package com.compassouol.catalogodeprodutos.domain.exception;

public class ProdutoJaCadastradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}