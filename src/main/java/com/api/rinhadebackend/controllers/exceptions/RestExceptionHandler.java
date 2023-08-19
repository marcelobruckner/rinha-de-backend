package com.api.rinhadebackend.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.rinhadebackend.services.exceptions.NotFoundException;
import com.api.rinhadebackend.services.exceptions.ParameterAbsentException;
import com.api.rinhadebackend.services.exceptions.ParameterTypeNotSupportedException;
import com.api.rinhadebackend.services.exceptions.UnprocessableEntityException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
        @Autowired
        private MessageSource messageSource;

        @ExceptionHandler(NotFoundException.class)
        protected ResponseEntity<Object> handleNotFound(NotFoundException ex,
                        WebRequest request) {
                HttpStatus status = HttpStatus.NOT_FOUND;
                ProblemType problemType = ProblemType.NOT_FOUND;
                String detail = ex.getMessage();

                Problem problem = createProblemBuilder(status, problemType, detail, request).userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(ParameterTypeNotSupportedException.class)
        protected ResponseEntity<Object> handleParameterTypeNotSupported(ParameterTypeNotSupportedException ex,
                        WebRequest request) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
                String detail = ex.getMessage();

                Problem problem = createProblemBuilder(status, problemType, detail, request).userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(ParameterAbsentException.class)
        protected ResponseEntity<Object> handleParameterAbsent(ParameterAbsentException ex, WebRequest request) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
                String detail = ex.getMessage();

                Problem problem = createProblemBuilder(status, problemType, detail, request).userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(UnprocessableEntityException.class)
        protected ResponseEntity<Object> handleUnprocessableEntity(UnprocessableEntityException ex,
                        WebRequest request) {
                HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
                ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
                String detail = ex.getMessage();

                Problem problem = createProblemBuilder(status, problemType, detail, request).userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
                        WebRequest request) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
                String detail = ex.getMessage();

                Problem problem = createProblemBuilder(status, problemType, detail, request).userMessage(detail)
                                .build();
                return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
                String detail = "Um ou mais campos inválidos.";

                BindingResult bindingResult = ex.getBindingResult();

                List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
                                .map(fieldError -> {
                                        String message = messageSource.getMessage(fieldError,
                                                        LocaleContextHolder.getLocale());
                                        return Problem.Field.builder()
                                                        .name(fieldError.getField())
                                                        .userMessage(message)
                                                        .build();
                                }).collect(Collectors.toList());

                HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;

                Problem problem = createProblemBuilder(statusCode, problemType, detail,
                                request)
                                .userMessage(detail)
                                .fields(problemFields)
                                .build();
                return super.handleExceptionInternal(ex, problem, headers, statusCode,
                                request);
        }

        private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail,
                        WebRequest request) {
                return Problem.builder()
                                .status(status.value())
                                .type(((ServletWebRequest) request).getRequest().getRequestURI())
                                .title(problemType.getTitle())
                                .detail(detail)
                                .timestamp(LocalDateTime.now());
        }
}
