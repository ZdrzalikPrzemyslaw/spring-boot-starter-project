package tech.zdrzalik.courses.model.AccountInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long>, AccountInfoRepositoryWithEM {
}
