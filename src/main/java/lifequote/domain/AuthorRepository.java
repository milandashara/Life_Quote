package lifequote.domain;

/**
 * Created by milanashara on 12/30/16.
 */

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "author", path = "/ws/author")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Cacheable(value = "author.findByName",sync = true)
    List<Author> findByName(@Param("name") String name);


}
