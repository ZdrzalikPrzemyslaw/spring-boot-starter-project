package tech.beetwin.stereoscopy.model.AccountInfo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.beetwin.stereoscopy.model.AbstractJpaRepository;

import java.util.List;
import java.util.Optional;


@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountInfoRepository extends AbstractJpaRepository<AccountInfoEntity>, AccountInfoRepositoryWithEM {
    boolean existsAccountInfoEntitiesByEmailEquals(String email);
    List<AccountInfoEntity> findAccountInfoEntitiesByEmail(String email);
    Optional<AccountInfoEntity> findAccountInfoEntityByEmail(String email);


}
