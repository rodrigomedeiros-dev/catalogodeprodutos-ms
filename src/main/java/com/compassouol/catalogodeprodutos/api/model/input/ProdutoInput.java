package com.compassouol.catalogodeprodutos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

    @ApiModelProperty(example = "Monitor Lg Ultrawide 25p", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "75 Hz, Resolução 2560 x 1080, Full HD.", required = true)
    @NotBlank
    private String description;

    @ApiModelProperty(example = "749.99", required = true)
    @NotNull
    private BigDecimal price;
}
