package br.com.zup.orange.talents.api.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.http.HttpMethod;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class ErrorDTO {
  private Instant timestamp;
  private Integer status;
  private String path;
  private HttpMethod method;
  private String message;
  private List<Map<String, String>> errors;
}
