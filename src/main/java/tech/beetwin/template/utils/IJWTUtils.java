package tech.beetwin.template.utils;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface IJWTUtils {
    public String getSecret();
    public long getDuration();
    String generateToken(@Nullable String subject, Map<String, Object> claims);
    public boolean validateToken(String token);
}
