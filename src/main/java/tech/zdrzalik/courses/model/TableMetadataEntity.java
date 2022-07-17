package tech.zdrzalik.courses.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "table_metadata", schema = "public")
public class TableMetadataEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_metadata_sequence")
    @SequenceGenerator(name = "table_metadata_sequence", sequenceName = "table_metadata_sequence", allocationSize = 1)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    public TableMetadataEntity setModifiedBy(AccountInfoEntity modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public AccountInfoEntity getCreatedBy() {
        return createdBy;
    }

    public TableMetadataEntity setCreatedBy(AccountInfoEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", referencedColumnName = "id", updatable = false, nullable = false)
    private AccountInfoEntity modifiedBy;
    @Basic
    @Column(name = "modification_date_time", nullable = true)
    private LocalDateTime modificationDateTime;
    @Basic
    @Column(name = "modified_by_ip", nullable = true, length = 256)
    private String modifiedByIp;

    @Basic
    @Column(name = "created_by_ip", nullable = true, length = 256)
    private String createdByIp;

    public TableMetadataEntity() {
    }

    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", updatable = false, nullable = false)
    private AccountInfoEntity createdBy;
    @Basic
    @Column(name = "created_date_time", nullable = true)
    private LocalDateTime createdDateTime;

    // TODO: 05/05/2022 Trzeba sprawić żeby ta wersja tutaj atumatycznie dzialala, ewentualnie dac wersje na kazda tabele ale to smutne
    @Basic
    @Version
    @Column(name = "version", nullable = true)
    private Long version;

    @PrePersist
    protected void onCreate() {
        if (createdDateTime == null ) {
            createdDateTime = LocalDateTime.now();
        }
        if (modificationDateTime == null ) {
            modificationDateTime = LocalDateTime.now();
        }
        if (version == null ) {
            version = 0L;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDateTime = LocalDateTime.now();
    }

    public AccountInfoEntity getModifiedBy() {
        return modifiedBy;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public TableMetadataEntity setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
        return this;
    }

    public String getModifiedByIp() {
        return modifiedByIp;
    }

    public TableMetadataEntity setModifiedByIp(String modifiedByIp) {
        this.modifiedByIp = modifiedByIp;
        return this;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public TableMetadataEntity setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public TableMetadataEntity setVersion(Long version) {
        this.version = version;
        return this;
    }

    public String getCreatedByIp() {
        return createdByIp;
    }

    public TableMetadataEntity setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
        return this;
    }
}