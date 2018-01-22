package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.ValidationUtil.getErrorResponse;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @Autowired
    MessageSource messageSource;

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo handleError(HttpServletRequest req, BindException e) {
        return new ErrorInfo(req.getRequestURL(), ErrorType.VALIDATION_ERROR, getErrorResponse(e.getBindingResult(), "<br>"));
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo handleError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return new ErrorInfo(req.getRequestURL(), ErrorType.VALIDATION_ERROR, getErrorResponse(e.getBindingResult(), "; "));
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String causeMessage = ValidationUtil.getRootCause(e).getLocalizedMessage();
        if (causeMessage.contains("users_unique_email_idx")) {
            return new ErrorInfo(req.getRequestURL(), ErrorType.DATA_ERROR, messageSource.getMessage("common.mailDuplicate", null, LocaleContextHolder.getLocale()));
        } else if (causeMessage.contains("meals_unique_user_datetime_idx")) {
            return new ErrorInfo(req.getRequestURL(), ErrorType.DATA_ERROR, "Meal at this time already exists.");
        }
        return logAndGetErrorInfo(req, e, true, ErrorType.DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.APP_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.toString());
    }
}