package tech.zdrzalik.courses.model.AccountInfo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.model.AbstractJpaRepository;


@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountInfoRepository extends AbstractJpaRepository<AccountInfoEntity>, AccountInfoRepositoryWithEM {
    Boolean existsAccountInfoEntitiesByEmailEquals(String email);
}
