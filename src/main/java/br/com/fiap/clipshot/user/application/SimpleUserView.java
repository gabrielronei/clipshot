package br.com.fiap.clipshot.user.application;

import br.com.fiap.clipshot.user.domain.User;

public final class SimpleUserView {

    private final Long id;
    private final String name;
    private final String email;

    public SimpleUserView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}