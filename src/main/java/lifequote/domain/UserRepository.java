package lifequote.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by milanashara on 1/3/17.
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(@Param("name") String name);
}
