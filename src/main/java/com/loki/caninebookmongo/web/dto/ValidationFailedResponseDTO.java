package com.loki.caninebookmongo.web.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationFailedResponseDTO {
    Map<String, String> errors;

}
