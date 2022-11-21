package tech.zdrzalik.courses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.zdrzalik.courses.exceptions.EntityNotFoundException;
import tech.zdrzalik.courses.model.AbstractEntity;
import tech.zdrzalik.courses.model.AbstractJpaRepository;

import java.util.Optional;

/**
 * A base class for other Service classes, provides basic methods which can be used for any Entity extending {@link AbstractEntity}.
 * @param <T> An entity class extending {@link AbstractEntity}
 */
public abstract class AbstractService<T extends AbstractEntity> {
    protected abstract AbstractJpaRepository<T> getRepository();

    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public T findById(Long id) {
        Optional<T> optional = getRepository().findById(id);
         return optional.orElseThrow(() -> EntityNotFoundException.entityNotFound(id));
    }
}
