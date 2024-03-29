package tech.beetwin.template.model.AccessLevel;

import tech.beetwin.template.model.AbstractEntity;
import tech.beetwin.template.model.TableMetadata.TableMetadataEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "access_levels", schema = "public", uniqueConstraints = {

})
public class AccessLevelsEntity extends AbstractEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_levels_sequence")
    @SequenceGenerator(name = "access_levels_sequence", sequenceName = "access_levels_sequence", allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_info_id", referencedColumnName = "id", updatable = false, nullable = false)
    private AccountInfoEntity accountInfoId;
    @Basic
    @Column(name = "level", nullable = false, length = 32)
    private AccessLevel level;
    @Basic
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public AccessLevelsEntity() {
        super();
    }

    public AccountInfoEntity getAccountInfoId() {
        return accountInfoId;
    }

    public AccessLevelsEntity setAccountInfoId(AccountInfoEntity accountInfoId) {
        this.accountInfoId = accountInfoId;
        return this;
    }

    public AccessLevel getLevel() {
        return level;
    }

    @Override
    public AccessLevelsEntity setVersion(Long version) {
        super.setVersion(version);
        return this;
    }

    public AccessLevelsEntity setLevel(AccessLevel level) {
        this.level = level;
        return this;
    }

    @Override
    public AccessLevelsEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        super.setTableMetadata(tableMetadata);
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AccessLevelsEntity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public AccessLevelsEntity setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
