package tech.zdrzalik.courses.model;

import javax.persistence.*;

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

    /**
     * Tworzy nową instancję klasy AbstractEntity.
     */
    public AbstractEntity() {
    }

    public TableMetadataEntity getTableMetadataEntity() {
        return tableMetadata;
    }

    public AbstractEntity setTableMetadata(TableMetadataEntity tableMetadata) {
        this.tableMetadata = tableMetadata;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }

}