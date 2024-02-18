package rs.raf.NotificationService.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import rs.raf.NotificationService.model.Notification;
import rs.raf.NotificationService.service.NotificationService;

import java.time.LocalTime;

@Component
public class ListenerHelper {

    @Autowired
    NotificationService notificationService;
    @Autowired
    JmsTemplate jmsTemplate;

    private String destinationForEmail;

    public ListenerHelper(JmsTemplate jmsTemplate, @Value("${destination.MessageforEmail}") String destination){
        this.jmsTemplate = jmsTemplate;
        this.destinationForEmail = destination;
    }

    @JmsListener(destination = "${destination.Message}")
    public void primiPoruku(String poruka){
        String[] delovi = poruka.split(",");
        String mojaPoruka = delovi[0].trim();
        String tipPoruke = delovi[1].trim();
        int id_korisnika = Integer.parseInt(delovi[2]);
        Notification notification = new Notification();
        notification.setTeks_notifikacije(mojaPoruka);
        notification.setId_korisnika(id_korisnika);
        notification.setTip_notifikacije(tipPoruke);
        LocalTime time = LocalTime.now();
        notification.setVreme_dodavanja(time);
        notificationService.sacuvaj(notification);
        jmsTemplate.convertAndSend(destinationForEmail,mojaPoruka + "," + tipPoruke);
    }


}
