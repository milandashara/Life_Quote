package lifequote.domain;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author milanashara
 */

@RepositoryRestResource(collectionResourceRel = "quote", path = "/ws/quote")
public interface QuoteRepository extends JpaRepository<Quote, Long> {


    //@Query("select quote from QuoteUIModel quote join Author author where author.id = ? 1")
    @Cacheable(value = "quote.findByAuthorId",sync = true )
    Page<Quote> findByAuthor_Id(@Param("authorId") Long authorId,Pageable pageable);

    @Cacheable(value = "quote.findByVirtueId",sync = true)
    Page<Quote> findByVirtue_Id(@Param("virtueId") Long authorId,Pageable pageable);

    @Cacheable(value = "quote.findByName",sync = true)
    List<Quote> findByName(@Param("name") String name);

    @Cacheable(value = "quote.findByName",sync = true)
    List<Quote> findByDescription(@Param("description") String name);

    @Override
    @Cacheable(value = "quote.findAll",sync = true)
    List<Quote> findAll();

    @Override
    @Cacheable(value = "quote.findAllsort",sync = true)
    List<Quote> findAll(Sort sort);

    @Override
    @Cacheable(value = "quote.findOne",sync = true)
    Quote findOne(Long aLong);

    Page<Quote>  findAll(Pageable pageable);

}