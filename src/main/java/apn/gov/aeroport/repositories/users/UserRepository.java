package apn.gov.aeroport.repositories.users;

import apn.gov.aeroport.domain.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsernameAndPassword(String username, String password);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

   // Long findMaxIdInRange(long base, long l);

  @Query("SELECT MAX(u.id) FROM User u WHERE u.id >= :min AND u.id < :max")
  Long findMaxIdInRange(@Param("min") Long min, @Param("max") Long max);

    List<User> findBySupprimFalse();
    List<User> findBySupprimTrue();

  // Pour le login, ne chercher que les utilisateurs non supprimés
  Optional<User> findByUsernameAndSupprimFalse(String username);
}