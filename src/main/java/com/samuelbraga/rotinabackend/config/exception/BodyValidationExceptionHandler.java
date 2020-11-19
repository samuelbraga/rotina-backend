package com.samuelbraga.rotinabackend.config.exception;

import com.samuelbraga.rotinabackend.config.exception.dto.ErrorBodyValidationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BodyValidationExceptionHandler {
  
  private final MessageSource messageSource;

  @Autowired
  public BodyValidationExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<ErrorBodyValidationDTO> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
    List<FieldError> fieldErrors =  methodArgumentNotValidException.getBindingResult().getFieldErrors();

    return fieldErrors.stream().map(fieldError -> {
      String message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      return new ErrorBodyValidationDTO(fieldError.getField(), message);
    }).collect(Collectors.toList());     
  }
}
