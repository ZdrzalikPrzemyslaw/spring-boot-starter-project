package tech.beetwin.stereoscopy.model.OauthAccountInfo;

import tech.beetwin.stereoscopy.model.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OauthAccountInfoRepositoryWithEMImpl extends AbstractRepository<OauthAccountInfoEntity> implements OauthAccountInfoRepositoryWithEM {
    @PersistenceContext
    private EntityManager em;

    public OauthAccountInfoRepositoryWithEMImpl() {
        super(OauthAccountInfoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
