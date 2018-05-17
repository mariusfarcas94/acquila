package com.acquila.common.dto.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representing a response type for the acquisition comments.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NewCommentDetails {

    @NotBlank
    private String acquisitionId;

    @NotBlank
    private String text;

}
