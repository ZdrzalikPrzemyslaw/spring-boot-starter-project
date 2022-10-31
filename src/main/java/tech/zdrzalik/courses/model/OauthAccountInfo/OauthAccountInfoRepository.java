package tech.zdrzalik.courses.model.OauthAccountInfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface OauthAccountInfoRepository extends AbstractJpaRepository<OauthAccountInfoEntity>, OauthAccountInfoRepositoryWithEM {
}
