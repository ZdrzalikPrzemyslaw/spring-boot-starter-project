package tech.zdrzalik.courses.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface UserInfoRepository extends AbstractJpaRepository<UserInfoEntity>, UserInfoRepositoryWithEM {
}
