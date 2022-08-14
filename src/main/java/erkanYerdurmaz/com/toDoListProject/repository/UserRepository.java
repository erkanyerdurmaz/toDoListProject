package erkanYerdurmaz.com.toDoListProject.repository;

import erkanYerdurmaz.com.toDoListProject.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {
    boolean existsByEmailAndPassword(String email, String password);

    User findByUserLastName(String username);
}
