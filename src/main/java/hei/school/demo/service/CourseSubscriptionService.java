package hei.school.demo.service;

import hei.school.demo.endpoint.rest.model.SubscribeUserRequest;
import hei.school.demo.endpoint.rest.model.SubscriptionResponse;
import hei.school.demo.repository.CourseRepository;
import hei.school.demo.repository.UserRepository;
import hei.school.demo.repository.model.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CourseSubscriptionService {

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  @Transactional
  public SubscriptionResponse subscribe(UUID courseId, SubscribeUserRequest request) {
    var course =
        courseRepository
            .findById(courseId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

    var user =
        userRepository
            .findById(request.id())
            .orElseGet(
                () -> {
                  var newUser = new User();

                  newUser.setId(request.id());
                  newUser.setFirstName(request.firstName());
                  newUser.setLastName(request.lastName());
                  newUser.setUserName(request.userName());
                  newUser.setEmail(request.email());

                  return newUser;
                });

    user.getCourses().add(course);

    userRepository.save(user);

    return new SubscriptionResponse(user.getId(), course.getId(), course.getTitle());
  }
}
