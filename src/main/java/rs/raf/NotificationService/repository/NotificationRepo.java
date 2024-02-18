package rs.raf.NotificationService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.raf.NotificationService.model.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Integer> {

    @Query("select n from Notification n where n.id_korisnika = :id_korisnika")
    List<Optional<Notification>> nadjiPoId(Integer id_korisnika);

}
