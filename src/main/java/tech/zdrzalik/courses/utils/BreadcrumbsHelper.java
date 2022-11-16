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

@Component("breadcrumbsHelper")
public class BreadcrumbsHelper {

    public List<Map.Entry<String, String>> getBreadcrumbs(HttpServletRequest httpServletRequest, WebMvcConfig.localeContextHolderWrapper holderWrapper) {
        var list = Arrays.stream(httpServletRequest.getServletPath().split("/")).filter(x -> !"".equals(x)).toList();
        List<Map.Entry<String, String>> listMap = new ArrayList<>();
        String url = "/" + holderWrapper.getLocaleContext().getLocale().toString();
        for (var item : list) {
            url += "/" + item;
            listMap.add(new AbstractMap.SimpleEntry<>(item, url));
        }
        return listMap;
    }



}
