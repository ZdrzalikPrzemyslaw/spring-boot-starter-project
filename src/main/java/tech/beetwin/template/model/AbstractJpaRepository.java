package tech.beetwin.template.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractJpaRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findFirstByOrderByIdDesc();
}
