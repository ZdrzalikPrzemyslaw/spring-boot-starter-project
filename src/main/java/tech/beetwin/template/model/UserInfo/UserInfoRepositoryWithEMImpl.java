package tech.beetwin.template.model.UserInfo;

import tech.beetwin.template.model.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserInfoRepositoryWithEMImpl extends AbstractRepository<UserInfoEntity> implements UserInfoRepositoryWithEM {
    @PersistenceContext
    private EntityManager em;

    public UserInfoRepositoryWithEMImpl() {
        super(UserInfoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
