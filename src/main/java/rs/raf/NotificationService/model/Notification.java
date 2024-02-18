package rs.raf.NotificationService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_notification;
    private String teks_notifikacije;
    private Integer id_korisnika;//id_korisnika kome je upucena notifikacija
    private String tip_notifikacije;
    private LocalTime vreme_dodavanja;


}
