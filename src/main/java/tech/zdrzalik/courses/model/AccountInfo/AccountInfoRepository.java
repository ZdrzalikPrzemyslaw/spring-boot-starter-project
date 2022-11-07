package tech.zdrzalik.courses.model.AccountInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

import java.util.List;
import java.util.Optional;


@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountInfoRepository extends AbstractJpaRepository<AccountInfoEntity>, AccountInfoRepositoryWithEM {
    List<AccountInfoEntity> findAccountInfoEntitiesByEmail(String email);
    AccountInfoEntity findAccountInfoEntityByEmail(String email);

}
