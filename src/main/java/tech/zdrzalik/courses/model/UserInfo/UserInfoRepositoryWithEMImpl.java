package tech.zdrzalik.courses.model.UserInfo;

import tech.zdrzalik.courses.model.AbstractRepository;

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
