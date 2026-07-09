package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.endpoint.rest.model.SubscribeUserRequest;
import hei.school.demo.endpoint.rest.model.SubscriptionResponse;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import hei.school.demo.service.CourseSubscriptionService;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CourseSubscriptionController {
//Subscription
  private final CourseSubscriptionService courseSubscriptionService;
  private final Mailer mailer;

  @PostMapping("/courses/{courseId}/subscribe")
  @ResponseStatus(HttpStatus.CREATED)
  @SneakyThrows
  public SubscriptionResponse subscribe(
      @PathVariable UUID courseId, @RequestBody SubscribeUserRequest request) {

    var subscription = courseSubscriptionService.subscribe(courseId, request);

    var recipientAddress = new InternetAddress(request.email());

    var email =
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Confirmation d'inscription",
            "<p>Bonjour "
                + request.firstName()
                + ",</p>"
                + "<p>Votre inscription au cours <strong>"
                + subscription.courseTitle()
                + "</strong> a été confirmée.</p>",
            List.of());

    mailer.accept(email);

    return subscription;
  }
}
