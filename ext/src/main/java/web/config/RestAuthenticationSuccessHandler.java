package main.java.web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.*;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            restClearAuthenticationAttributes(request, response);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() ||
                (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            restClearAuthenticationAttributes(request, response);
            return;
        }

        restClearAuthenticationAttributes(request, response);
    }

    private void restClearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.getWriter().print(String.format("{\"token\":\"%s\"}", session.getId()));
        response.getWriter().flush();
        clearAuthenticationAttributes(request);
    }

}
