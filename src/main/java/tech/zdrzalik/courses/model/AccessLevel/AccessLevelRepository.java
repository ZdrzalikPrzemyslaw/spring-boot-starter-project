package tech.zdrzalik.courses.model.AccessLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface AccessLevelRepository extends JpaRepository<AccessLevelsEntity, Long>, AccessLevelRepositoryWithEM {
}
