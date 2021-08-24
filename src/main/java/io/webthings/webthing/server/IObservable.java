package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;

import java.util.List;

/**
 * @author Lorenzo
 */
public interface IObservable {
    void addSuscriber(NanoWSD.WebSocket ws);

    void removeSubscriber(NanoWSD.WebSocket ws);

    List<NanoWSD.WebSocket> getSubscribers();
}
