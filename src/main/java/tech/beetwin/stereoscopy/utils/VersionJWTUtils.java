package tech.beetwin.stereoscopy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.beetwin.stereoscopy.model.AbstractEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class VersionJWTUtils extends AbstractJwtUtils {

    @Value("${jwt.secret.version}")
    private String secret;

    @Value("${jwt.validity.version}")
    public long duration;

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    public String generateToken(AbstractEntity entity) {
        Long id = entity.getId();
        Long version = entity.getVersion();
        Integer hashedName = Objects.hash(entity.getClass().getSimpleName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("version", version);
        claims.put("name", hashedName);
        return generateToken(null, claims);
    }
}
