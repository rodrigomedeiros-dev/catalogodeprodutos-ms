package com.compassouol.catalogodeprodutos.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Monitor Lg Ultrawide 25p")
    private String name;

    @ApiModelProperty(example = "75 Hz, Resolução 2560 x 1080, Full HD.")
    private String description;

    @ApiModelProperty(example = "749.99")
    private BigDecimal price;

}
