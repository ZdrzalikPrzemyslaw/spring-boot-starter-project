package tech.zdrzalik.courses.filters;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.zdrzalik.courses.common.Codes;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Component
public class PathVariableLocaleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));
        String[] variables = url.split("/");

        if (variables.length > 1 && isLocale(variables[1])) {
            request.setAttribute(Codes.LOCALE_ATTRIBUTE_NAME, variables[1]);
            String newUrl = StringUtils.removeStart(url, '/' + variables[1]);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isLocale(String locale) {
        //validate the string here against an accepted list of locales or whatever
        try {
            LocaleUtils.toLocale(locale);
            return true;
        } catch (IllegalArgumentException e) {

        }
        return false;
    }
}
