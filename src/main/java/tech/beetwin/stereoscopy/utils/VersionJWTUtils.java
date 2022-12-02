package tech.beetwin.stereoscopy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.beetwin.stereoscopy.model.AbstractEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component(value = ApplicationContextUtils.VERSION_JWT_COMPONENT)
public class VersionJWTUtils extends AbstractJwtUtils {

    private final Logger logger = LoggerFactory.getLogger(VersionJWTUtils.class);
    @Value("${jwt.secret.version}")
    private String secret;

    @Value("${jwt.validity.version}")
    private long duration;

    public VersionJWTUtils() {
    }

    public VersionJWTUtils(String secret, long duration) {
        this.secret = secret;
        this.duration = duration;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    public VersionJWTUtils setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    public VersionJWTUtils setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public String generateToken(AbstractEntity entity) {
        Long id = entity.getId();
        Long version = entity.getVersion();
        Integer hashedName = Objects.hash(entity.getClass().getSimpleName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("version", version);
        claims.put("nameHash", hashedName);
        return generateToken(null, claims);
    }

    public Long getVersion(String token) {
        return super.getAllClaimsFromToken(token).get("version", Long.class);
    }

    public Integer getNameHash(String token) {
        return super.getAllClaimsFromToken(token).get("nameHash", Integer.class);
    }

    public Long getId(String token) {
        return super.getAllClaimsFromToken(token).get("id", Long.class);
    }

    @Override
    protected Logger getLogger() {
        return this.logger;
    }
}
