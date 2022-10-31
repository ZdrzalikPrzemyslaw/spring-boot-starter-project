package tech.zdrzalik.courses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

import java.util.Optional;

public abstract class AbstractService<T> {
    protected abstract AbstractJpaRepository<T> getRepository();
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public T findById(Long id) {
        Optional<T> optional = getRepository().findById(id);
         return optional.orElseThrow(() -> {
            return EntityNotFoundException.entityNotFound(getRepository().getClass(),id);
        });
    }
}
