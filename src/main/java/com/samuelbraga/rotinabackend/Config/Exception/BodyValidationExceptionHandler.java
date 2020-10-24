package com.samuelbraga.rotinabackend.Config.Exception;

import com.samuelbraga.rotinabackend.Config.Exception.DTO.ErrorBodyValidationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
    List<ErrorBodyValidationDTO> errorBodyValidationDTOS = new ArrayList<>();
    
    List<FieldError> fieldErrors =  methodArgumentNotValidException.getBindingResult().getFieldErrors();
    
    fieldErrors.forEach(error -> {
      String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      ErrorBodyValidationDTO errorBodyValidation = new ErrorBodyValidationDTO(error.getField(), message);
      errorBodyValidationDTOS.add(errorBodyValidation);
    });
   
    return errorBodyValidationDTOS;
  }
}
