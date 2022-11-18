package tech.zdrzalik.courses.model.AccessLevel;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface AccessLevelRepository extends AbstractJpaRepository<AccessLevelsEntity>, AccessLevelRepositoryWithEM {

}
