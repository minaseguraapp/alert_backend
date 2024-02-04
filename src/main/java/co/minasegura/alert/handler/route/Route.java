package co.minasegura.alert.handler.route;

import com.amazonaws.HttpMethod;

public record Route(HttpMethod httpMethod, String path){}