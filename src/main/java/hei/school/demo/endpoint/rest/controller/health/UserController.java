package hei.school.demo.endpoint.rest.controller.health;

import hei.school.demo.endpoint.rest.controller.health.model.UserRegistrationRequest;
import hei.school.demo.endpoint.rest.controller.health.model.UserRegistrationResponse;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final Mailer mailer;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @SneakyThrows
  public UserRegistrationResponse register(@RequestBody UserRegistrationRequest request) {
    var recipientAddress = new InternetAddress(request.email());

    var email =
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Bienvenue sur notre plateforme",
            "<p>Bonjour "
                + request.name()
                + ",</p>"
                + "<p>Votre inscription a été effectuée avec succès.</p>",
            List.of());

    mailer.accept(email);

    return new UserRegistrationResponse(request.name(), request.email());
  }
}
