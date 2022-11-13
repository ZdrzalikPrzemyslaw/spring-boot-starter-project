package tech.zdrzalik.courses.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractJpaRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findFirstByOrderByIdDesc();
}
