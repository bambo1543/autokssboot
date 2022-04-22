package de.promove.autokss.web.view.entity;

import de.promove.autokss.model.User;
import de.promove.autokss.service.EmailService;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.NavigationOutcome;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@ViewScope
@Getter
@Setter
public class ForgottenPasswordView {

    private Logger logger = LoggerFactory.getLogger(ForgottenPasswordView.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String email;

    public String resetPassword() {
        User user = userService.findUserByUsername(email);
        if(user != null) {
            String password = UUID.randomUUID().toString();
//            emailService.sendSimpleMessage(email, "Password reset", password);
            logger.warn(password);

            user.setPassword(password);
            userService.mergeAndEncode(user);
        }
        return NavigationOutcome.LOGIN.getOutcome();
    }

}
