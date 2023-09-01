package tech.beetwin.template.model.UserInfo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.beetwin.template.model.AbstractJpaRepository;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface UserInfoRepository extends AbstractJpaRepository<UserInfoEntity>, UserInfoRepositoryWithEM {
}
