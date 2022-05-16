package de.promove.autokss.web.view.entity;

import de.promove.autokss.model.User;
import de.promove.autokss.service.EmailService;
import de.promove.autokss.service.UserService;
import de.promove.autokss.web.scope.ViewScope;
import de.promove.autokss.web.util.MessageFactory;
import de.promove.autokss.web.util.NavigationOutcome;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@ViewScope
public class ForgottenPasswordView {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Getter
    @Setter
    private String email;

    public String resetPassword() {
        User user = userService.findUserByUsername(email);
        if(user != null) {
            String password = UUID.randomUUID().toString();
            emailService.sendSimpleMessage(email, MessageFactory.getMessage("password.reset.subject"),
                    MessageFactory.getMessage("password.reset.message", password));
            user.setPassword(password);
            userService.mergeAndEncode(user);
            return NavigationOutcome.LOGIN.getOutcome(true, "passwordResetSuccessful=true");
        }
        return NavigationOutcome.LOGIN.getOutcome(true, "passwordResetFailed=true");
    }

}
