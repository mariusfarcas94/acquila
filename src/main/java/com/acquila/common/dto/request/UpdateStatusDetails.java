package com.acquila.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contains details needed to update the status of an acquisition.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDetails {

    private String acquisitionId;

    private String newStatus;

    private String type;
}
