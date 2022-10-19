package tech.zdrzalik.courses.model.AccountInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long>, AccountInfoRepositoryWithEM {
}
