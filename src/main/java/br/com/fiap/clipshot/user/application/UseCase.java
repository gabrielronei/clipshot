package br.com.fiap.clipshot.user.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}