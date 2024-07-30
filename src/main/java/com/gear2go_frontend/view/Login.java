package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.AuthenticationRequest;
import com.gear2go_frontend.service.UserService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

@Route(value = "login")
public class Login extends Div {

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setError(true);
        add(loginOverlay);
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(e -> {
            userService.getAuthenticationToken(new AuthenticationRequest(
                    e.getUsername(),
                    e.getPassword()
            ));
            getUI().ifPresent(ui -> ui.navigate(ProductView.class));
        });
    }
}