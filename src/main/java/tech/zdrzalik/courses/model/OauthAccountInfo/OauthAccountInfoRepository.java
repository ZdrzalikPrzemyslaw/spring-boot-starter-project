package tech.zdrzalik.courses.model.OauthAccountInfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface OauthAccountInfoRepository extends JpaRepository<OauthAccountInfoEntity, Long>, OauthAccountInfoRepositoryWithEM {
}
