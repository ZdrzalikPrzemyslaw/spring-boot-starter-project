package tech.zdrzalik.courses.utils;

import org.springframework.stereotype.Component;
import tech.zdrzalik.courses.config.WebMvcConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class used in thymeleaf templates to generate breadcrumbs.
 */
@Component("breadcrumbsHelper")
public class BreadcrumbsHelper {
    /**
     *
     * @param httpServletRequest Defaults to being {@link org.springframework.beans.factory.annotation.Autowired}. Used to get the {@link HttpServletRequest#getServletPath()}.
     * @param holderWrapper Defaults to being  {@link org.springframework.beans.factory.annotation.Autowired}. Used to add current locale to the breadcrumbs URI.
     * @return {@link List} of {@link Map.Entry} where the keys are the '/' separated parts of the URI, and the values are the URL to the given breadcrumbs, relative to the current ServletContext.
     */
    public List<Map.Entry<String, String>> getBreadcrumbs(HttpServletRequest httpServletRequest, LocaleContextHolderWrapper holderWrapper) {
        var list = Arrays.stream(httpServletRequest.getServletPath().split("/")).filter(x -> !"".equals(x)).toList();
        List<Map.Entry<String, String>> listMap = new ArrayList<>();
        String url = "/" + (Objects.isNull(holderWrapper.getLocaleContext().getLocale())
                                ? "" : holderWrapper.getLocaleContext().getLocale().toString());
        for (var item : list) {
            url += "/" + item;
            listMap.add(new AbstractMap.SimpleEntry<>(item, url));
        }
        return listMap;
    }



}
