package tech.beetwin.template.model.TableMetadata;

import tech.beetwin.template.model.AbstractRepository;

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
