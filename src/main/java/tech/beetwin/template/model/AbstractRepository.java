package tech.beetwin.template.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.MessageFormat;
import java.util.Optional;

public abstract class AbstractRepository<T> {

    private final Class<T> entityClass;

    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();


    protected static <T> Optional<T> findOrEmpty(final DaoRetriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            Logger logger = LoggerFactory.getLogger(AbstractRepository.class);
            logger.debug(MessageFormat.format("FindOrEmpty threw {0} from {1}", ex, retriever));
        }
        return Optional.empty();
    }



    @FunctionalInterface
    public interface DaoRetriever<T> {
        T retrieve() throws NoResultException;
    }
}