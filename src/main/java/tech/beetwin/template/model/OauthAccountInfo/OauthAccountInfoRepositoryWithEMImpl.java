package tech.beetwin.template.model.OauthAccountInfo;

import tech.beetwin.template.model.AbstractRepository;

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
