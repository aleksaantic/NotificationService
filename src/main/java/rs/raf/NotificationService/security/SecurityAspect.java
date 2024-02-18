package rs.raf.NotificationService.security;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.raf.NotificationService.model.Notification;
import rs.raf.NotificationService.repository.NotificationRepo;
import rs.raf.NotificationService.service.NotificationService;
import rs.raf.NotificationService.tokenService.Token;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {

    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    Token token;

    @Around("@annotation(rs.raf.NotificationService.security.CheckSecurity)")
    public Object arond(ProceedingJoinPoint joinPoint) throws Throwable{

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();


        Object[] arguments = joinPoint.getArgs();
        Integer id_korisnika = null;
        for (Object argument: arguments){
            if(argument instanceof Integer){
                id_korisnika = (Integer) argument;
                break;
            }
        }

        String token1 = null;
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){

                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token1 = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }
        if (token1 == null) {
            return new ResponseEntity<>("Korisnik nije autorizovan", HttpStatus.UNAUTHORIZED);
        }
        Claims claims = token.parseToken(token1);

        if (claims == null) {
            return new ResponseEntity<>("Korisnik nije autorizovan",HttpStatus.UNAUTHORIZED);
        }

        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        String role = claims.get("role", String.class);
        if(Arrays.asList(checkSecurity.uloge()).contains(role)){
            Integer id_user = claims.get("id", Integer.class);
            if(id_user.equals(id_korisnika)){
                return joinPoint.proceed();
            }else{
                return new ResponseEntity<>("Korisnik nema permisiju za trazeni zahtev" +
                        "",HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("Korisnik nema permisiju za trazeni zahtev" +
                "",HttpStatus.FORBIDDEN);
    }
}
