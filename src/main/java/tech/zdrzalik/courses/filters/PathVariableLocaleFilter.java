package tech.zdrzalik.courses.filters;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.zdrzalik.courses.common.I18nCodes;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int index = 0;
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));
        System.out.println(url);
        String[] variables = Arrays.stream(url.split("/")).filter(x -> !Strings.isBlank(x)).toArray(String[]::new);
        if (variables.length > index && isLocale(variables[index])) {
            request.setAttribute(I18nCodes.LOCALE_ATTRIBUTE_NAME, variables[index]);
            String newUrl = StringUtils.removeStart(url, '/' + variables[index]);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isLocale(String locale) {
        if (Objects.equals(locale, "css")) return false;
        //validate the string here against an accepted list of locales or whatever
        try {
            LocaleUtils.toLocale(locale);
            return true;
        } catch (IllegalArgumentException e) {

        }
        return false;
    }
}
