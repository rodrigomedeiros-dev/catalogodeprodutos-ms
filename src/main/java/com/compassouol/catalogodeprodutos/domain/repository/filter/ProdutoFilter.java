package com.compassouol.catalogodeprodutos.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoFilter {
    private Long min_price;
    private Long max_price;
    private String q;
}
