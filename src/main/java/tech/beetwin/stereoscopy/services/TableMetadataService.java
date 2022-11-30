package tech.beetwin.stereoscopy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.beetwin.stereoscopy.model.AbstractEntity;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoRepository;
import tech.beetwin.stereoscopy.model.TableMetadata.TableMetadataEntity;
import tech.beetwin.stereoscopy.model.TableMetadata.TableMetadataRepository;
import tech.beetwin.stereoscopy.security.UserDetailsImpl;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TableMetadataService {

    @Autowired
    private TableMetadataRepository tableMetadataRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    public void updateMetadata(UpdateEntityEvent updateEntityEvent) {
        var entity = updateEntityEvent.getAbstractEntity().getTableMetadataEntity();
        entity
            .setModificationDateTime(LocalDateTime.now())
            .setModifiedByIp(getCurrentRequestIp())
            .setModifiedByNoNull(updateEntityEvent.getEditor() == null ? getCurrentUser().orElse(null) : updateEntityEvent.getEditor());
        if (updateEntityEvent.isUpdateCreated()) {
            entity
                .setCreatedDateTime(LocalDateTime.now())
                .setCreatedByIp(getCurrentRequestIp())
                .setCreatedByNoNull(updateEntityEvent.getEditor() == null ? getCurrentUser().orElse(null) : updateEntityEvent.getEditor());
        }
        tableMetadataRepository.save(entity);
    }

    /**
     * Only use in tests!
     */
    public void wipeAllMetadataCreatedModified() {
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("prod")) {
            throw new IllegalStateException("Cant run in prod");
        }
        for (TableMetadataEntity tableMetadataEntity : tableMetadataRepository.findAll()) {
            tableMetadataEntity.setCreatedBy(null);
            tableMetadataEntity.setModifiedBy(null);
            tableMetadataRepository.save(tableMetadataEntity);
        }
    }

    private String getCurrentRequestIp() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    private Optional<AccountInfoEntity> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken || authentication.getPrincipal() == null) return Optional.empty();
        if (authentication.getPrincipal() instanceof UserDetailsImpl impl) {
            return accountInfoRepository.findById(impl.getId());
        }
        return Optional.empty();

    }

    public static class UpdateEntityEvent {
        private final AbstractEntity abstractEntity;
        @Nullable
        private AccountInfoEntity editor;
        private boolean updateCreated = false;

        public UpdateEntityEvent(AbstractEntity abstractEntity) {
            this.abstractEntity = abstractEntity;
        }

        @Nullable
        public AccountInfoEntity getEditor() {
            return editor;
        }

        public UpdateEntityEvent setEditor(@Nullable AccountInfoEntity editor) {
            this.editor = editor;
            return this;
        }

        public AbstractEntity getAbstractEntity() {
            return abstractEntity;
        }

        public boolean isUpdateCreated() {
            return updateCreated;
        }

        public UpdateEntityEvent setUpdateCreated(boolean updateCreated) {
            this.updateCreated = updateCreated;
            return this;
        }
    }


}
