package com.acquila.common.dto.response;

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
public class CommentDetails {

    String type;

    String text;

    String author;

    String date;
}
