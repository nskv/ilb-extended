package main.java.web.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Optional.ofNullable(request.getSession()).ifPresent(HttpSession::invalidate);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed: " + authException.getMessage());
    }
}
