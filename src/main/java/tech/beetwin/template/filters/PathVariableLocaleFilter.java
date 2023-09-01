package tech.beetwin.template.filters;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.beetwin.template.common.I18nCodes;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Component
public class PathVariableLocaleFilter extends OncePerRequestFilter {
    @Value("${app.locale.default:pl}")
    private String defaultLocale;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int index = 0;
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));
        String[] variables = Arrays.stream(url.split("/")).filter(x -> !Strings.isBlank(x)).toArray(String[]::new);
        if (variables.length > index && (variables[index].equals("css") || variables[index].equals("images"))){
            // static files in /resources/static directories. Need to be updated if more directories added, preferably make it work automatically with the directories loaded on app start.
            filterChain.doFilter(request, response);
        }
        else if (variables.length > index && isLocale(variables[index])) {
            request.setAttribute(I18nCodes.LOCALE_ATTRIBUTE_NAME, variables[index]);
            String newUrl = StringUtils.removeStart(url, '/' + variables[index]);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } else if (request.getHeader("Accept").contains("text/html")) {
            response.sendRedirect("/" + defaultLocale + url);
        } else {
            filterChain.doFilter(request,response);
        }
    }

    private boolean isLocale(String locale) {
        try {
            LocaleUtils.toLocale(locale);
            return true;
        } catch (IllegalArgumentException e) {
            LogFactory.getLog(this.getClass()).trace("Invalid locale tag in isLocale method of PathVariableLocaleFilter " , e);
        }
        return false;
    }
}
