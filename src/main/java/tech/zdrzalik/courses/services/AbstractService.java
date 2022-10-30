package tech.zdrzalik.courses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.zdrzalik.courses.model.AbstractJpaRepository;
import tech.zdrzalik.courses.model.AbstractRepository;

public abstract class AbstractService<T> {
    protected abstract AbstractJpaRepository<T> getRepository();
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }
}
