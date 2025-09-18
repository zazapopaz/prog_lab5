package org.example.server;

import org.example.base.response.ClientCommandRequest;

import java.net.InetSocketAddress;


public record ClientRequestWithIP(ClientCommandRequest clientResponse, InetSocketAddress ip) {
}
