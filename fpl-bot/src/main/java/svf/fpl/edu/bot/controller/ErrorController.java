package svf.fpl.edu.bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ErrorController implements AccessDeniedHandler {

    /**
     * Generates an Error page by intercepting exceptions generated from HttpFirewall.
     *
     * @param ex A RequestRejectedException exception.
     * @return The tile definition name for the page.
     */
    @ExceptionHandler(RequestRejectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRequestRejectedException(final HttpServletRequest request, final RequestRejectedException ex) {
        log.warn("Rejected request for [" + request.getRequestURL().toString() + "]. Reason: " + ex.getMessage());
        return "error";
    }

    /**
     * Generates a Server Error page.
     *
     * @param ex An exception.
     * @return The tile definition name for the page.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(final Exception ex) {
        log.error("Server Error", ex);
        return "error";
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.info("User '" + auth.getName() + "' attempted to access the protected URL: " + httpServletRequest.getRequestURI());
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");
    }
}
