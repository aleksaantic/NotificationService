package rs.raf.NotificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.raf.NotificationService.model.Notification;
import rs.raf.NotificationService.repository.NotificationRepo;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public Notification sacuvaj(Notification notification){
        return notificationRepo.save(notification);
    }

    public List<Optional<Notification>> notifikacijeZaKorisnika(Integer id_korisnika){

        return notificationRepo.nadjiPoId(id_korisnika);
    }
}
