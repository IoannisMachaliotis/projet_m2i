package com.matawan.nicefc.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code PlayerDto} class represents a Data Transfer Object (DTO) for player information.
 * It is used to transfer data related to a player between different layers of the application.

 * The class includes validation annotations from Jakarta Bean Validation API to enforce
 * constraints on the fields. The {@code @NotEmpty} and {@code @Size} annotations
 * ensure that the 'name' and 'position' fields meet specific criteria.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    /**
     * The name of the player. It must not be blank and should be between 3 and 30 characters.
     */
    @NotEmpty(message = "name cannot be blank")
    @Size(min = 3,max = 30,message = "name size must be between 3 and 30 characters")
    private String name;

    /**
     * The position of the player. It must not be blank and should have 2 to 3 characters.
     */
    @NotEmpty(message = "position cannot be blank")
    @Size(min = 2,max = 3,message = "position size must have 2 to 3 characters")
    private String position;
}
