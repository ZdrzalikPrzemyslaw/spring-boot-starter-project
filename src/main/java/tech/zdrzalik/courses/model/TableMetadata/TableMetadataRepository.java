package tech.zdrzalik.courses.model.TableMetadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository()
@Transactional(propagation = Propagation.MANDATORY)
public interface TableMetadataRepository extends JpaRepository<TableMetadataEntity, Long>, TableMetadataRepositoryWithEM {
}
