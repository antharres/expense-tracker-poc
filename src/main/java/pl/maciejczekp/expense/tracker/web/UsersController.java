package pl.maciejczekp.expense.tracker.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.maciejczekp.expense.tracker.usecase.users.RegisterUserUseCase;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users/")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApplicationResponse<String>> createUser(@Valid @RequestBody RegisterUserUseCase.RegisterUserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApplicationResponse.error(
                    bindingResult.getFieldErrors().stream().map(FieldError::toString).collect(Collectors.joining("<br />"))
            ));
        }
        try {
            registerUserUseCase.execute(request);
            return ResponseEntity.ok(
                    ApplicationResponse.success("User has been registered")
            );
        } catch (RegisterUserUseCase.UserAlreadyExistsException e) {
            LOGGER.info("Error when registering user", e);
            return ResponseEntity.badRequest().body(
                    ApplicationResponse.error(e.getMessage())
            );
        }
    }

    @RequestMapping("current/")
    public ResponseEntity<ApplicationResponse<Principal>> user(Principal user) {
        return ResponseEntity.ok(
                ApplicationResponse.success(user)
        );
    }
}
