package rs.raf.NotificationService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.NotificationService.model.Notification;
import rs.raf.NotificationService.repository.NotificationRepo;
import rs.raf.NotificationService.security.CheckSecurity;
import rs.raf.NotificationService.service.NotificationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepo notificationRepo;

    @GetMapping(value = "/{id_korisnika}")
    @CheckSecurity(uloge = {"Administrator","Nastavnik"})
    public ResponseEntity<List<Optional<Notification>>> notifikacije(@RequestHeader("Authorization") String authorization,

                                                                         @PathVariable Integer id_korisnika){

        return ResponseEntity.ok(notificationService.notifikacijeZaKorisnika(id_korisnika));
    }
}
