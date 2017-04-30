package lifequote.domain;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author milanashara
 */
@RepositoryRestResource(collectionResourceRel = "virtue", path = "/ws/virtue")
public interface VirtueRepository extends JpaRepository<Virtue, Long> {

    @Cacheable(value = "virtue.findByName",sync = true )
    List<Virtue> findByName(@Param("name") String name);

}