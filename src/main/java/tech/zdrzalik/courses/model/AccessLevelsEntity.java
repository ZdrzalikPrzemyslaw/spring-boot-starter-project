package tech.zdrzalik.courses.model;

import javax.persistence.*;

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
    private String level;
    @Basic
    @Column(name = "enabled", nullable = false)
    private boolean enabled;


    public AccessLevelsEntity setId(long id) {
        this.id = id;
        return this;
    }

    public AccountInfoEntity getAccountInfoId() {
        return accountInfoId;
    }

    public AccessLevelsEntity setAccountInfoId(AccountInfoEntity accountInfoId) {
        this.accountInfoId = accountInfoId;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public AccessLevelsEntity() {
    }

    @Override
    public AccessLevelsEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        super.setTableMetadata(tableMetadata);
        return this;
    }

    public AccessLevelsEntity setLevel(String level) {
        this.level = level;
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
}
