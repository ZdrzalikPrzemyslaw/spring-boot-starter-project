package tech.beetwin.template.model;

import tech.beetwin.template.model.TableMetadata.TableMetadataEntity;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass()
public abstract class AbstractEntity {

    /**
     * Pobiera wartośc pola ID.
     *
     * @return ID
     */
    public abstract Long getId();

    // to bycie tutaj tego utrudnia zrobinie unique
    @OneToOne(optional = true, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "table_metadata_id", referencedColumnName = "id", updatable = false, nullable = false, unique = true)
    private TableMetadataEntity tableMetadata;

    @Basic
    @Version
    @Column(name = "version", nullable = true)
    private Long version;

    /**
     * Tworzy nową instancję klasy AbstractEntity.
     */
    protected AbstractEntity() {
    }

    public TableMetadataEntity getTableMetadataEntity() {
        return tableMetadata;
    }

    public AbstractEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        this.tableMetadata = tableMetadata;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity that)) return false;
        return (Objects.equals(getVersion(), that.getVersion()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getVersion() {
        return version;
    }

    public AbstractEntity setVersion(Long version) {
        this.version = version;
        return this;
    }
}