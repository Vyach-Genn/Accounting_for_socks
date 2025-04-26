package pro_sky.accounting_for_socks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro_sky.accounting_for_socks.model.entity.Sock;

import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

    Optional<Sock> findByColorAndCottonPart(String color, Integer cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE s.color = :color AND s.cottonPart > :cottonPart")
    Integer sumQuantityByColorAndCottonPartMoreThan(String color, Integer cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE s.color = :color AND s.cottonPart < :cottonPart")
    Integer sumQuantityByColorAndCottonPartLessThan(String color, Integer cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Sock s WHERE s.color = :color AND s.cottonPart = :cottonPart")
    Integer sumQuantityByColorAndCottonPartEqual(String color, Integer cottonPart);
}
