package tech.beetwin.template.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        StringBuilder url = new StringBuilder()
                .append("/")
                .append((null != holderWrapper.getLocaleContext().getLocale())
                        ? holderWrapper.getLocaleContext().getLocale().toString() : "");
        for (var item : list) {
            url.append("/").append(item);
            listMap.add(new AbstractMap.SimpleEntry<>(item, url.toString()));
        }
        return listMap;
    }



}
