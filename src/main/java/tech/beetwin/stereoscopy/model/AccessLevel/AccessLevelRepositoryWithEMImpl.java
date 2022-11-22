package tech.beetwin.stereoscopy.model.AccessLevel;

import tech.beetwin.stereoscopy.model.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AccessLevelRepositoryWithEMImpl extends AbstractRepository<AccessLevelsEntity> implements AccessLevelRepositoryWithEM {

    @PersistenceContext
    private EntityManager em;

    public AccessLevelRepositoryWithEMImpl() {
        super(AccessLevelsEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
