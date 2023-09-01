package tech.beetwin.template.model.AccountInfo;

import tech.beetwin.template.model.AbstractRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AccountInfoRepositoryWithEMImpl extends AbstractRepository<AccountInfoEntity> implements AccountInfoRepositoryWithEM {

    @PersistenceContext
    private EntityManager em;

    public AccountInfoRepositoryWithEMImpl() {
        super(AccountInfoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
