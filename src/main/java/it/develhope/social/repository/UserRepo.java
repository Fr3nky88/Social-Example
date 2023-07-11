package it.develhope.social.repository;

import it.develhope.social.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByNome(String nome);
    List<User> findByCognomeOrderByIdAsc(String cognome);

    List<User> findByCognomeOrderByIdDesc(String cognome);

    List<User> findByNomeAndCognomeOrderByIdAsc(String nome, String cognome);

    @Query("SELECT u FROM User u WHERE u.nome LIKE %:letter%")
    List<User> findContainsLetter(@Param("letter") String letter);

    Optional<List<User>> findByEmailContainingIgnoreCase(String email);

}
