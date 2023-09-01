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
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Component
public class PathVariableLocaleFilter extends OncePerRequestFilter {
    @Value("${app.locale.default:pl}")
    private String defaultLocale;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int index = 0;
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));
        String[] splitUrl = Arrays.stream(url.split("/")).filter(x -> !Strings.isBlank(x)).toArray(String[]::new);
        if (splitUrl.length > index && splitUrl[0].equals("api")) {
            index = 1;
        }
        if (splitUrl.length > index && (splitUrl[index].equals("css") || splitUrl[index].equals("images"))){
            // static files in /resources/static directories. Need to be updated if more directories added, preferably make it work automatically with the directories loaded on app start.
            filterChain.doFilter(request, response);
        }
        else if (splitUrl.length > index && isLocale(splitUrl[index])) {
            request.setAttribute(I18nCodes.LOCALE_ATTRIBUTE_NAME, splitUrl[index]);
            String newUrl = StringUtils.removeStart(url, '/' + splitUrl[index]);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } else if (nullSafeContains(request.getHeader("Accept"), "text/html")) {
            // Only for browsers
            response.sendRedirect(request.getContextPath() + "/" + defaultLocale + url);
        } else {
            filterChain.doFilter(request,response);
        }
    }

    private static boolean nullSafeContains(String str, String substr) {
        return str != null && str.contains(substr);
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
