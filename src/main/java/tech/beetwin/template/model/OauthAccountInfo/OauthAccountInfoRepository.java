package tech.beetwin.template.model.OauthAccountInfo;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.beetwin.template.model.AbstractJpaRepository;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface OauthAccountInfoRepository extends AbstractJpaRepository<OauthAccountInfoEntity>, OauthAccountInfoRepositoryWithEM {
}
