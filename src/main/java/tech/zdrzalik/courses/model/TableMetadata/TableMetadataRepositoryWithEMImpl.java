package tech.zdrzalik.courses.model.TableMetadata;

import tech.zdrzalik.courses.model.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TableMetadataRepositoryWithEMImpl extends AbstractRepository<TableMetadataEntity> implements TableMetadataRepositoryWithEM {

    @PersistenceContext
    private EntityManager em;

    public TableMetadataRepositoryWithEMImpl() {
        super(TableMetadataEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
