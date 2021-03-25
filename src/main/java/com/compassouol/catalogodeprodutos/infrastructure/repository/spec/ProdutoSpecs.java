package com.compassouol.catalogodeprodutos.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import com.compassouol.catalogodeprodutos.domain.model.Produto;
import com.compassouol.catalogodeprodutos.domain.repository.filter.ProdutoFilter;
import java.util.ArrayList;

public class ProdutoSpecs {

    public static Specification<Produto> usandoFiltro(ProdutoFilter filtro){
        return (root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            //adicionar predicates no arrayList
            if(filtro.getMin_price() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"),filtro.getMin_price()));
            }

            if(filtro.getMax_price() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"),filtro.getMax_price()));
            }

            if(filtro.getQ() != null) {
                predicates.add(builder.or(builder.like(root.get("name"), "%" + filtro.getQ() + "%"), builder.like(root.get("description"), "%" + filtro.getQ() + "%")));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
