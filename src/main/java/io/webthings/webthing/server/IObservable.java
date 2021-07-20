/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;
import java.util.List;

/**
 *
 * @author Lorenzo
 */
public interface IObservable {
    public void addSuscriber(NanoWSD.WebSocket ws);
    public void removeSubscriber(NanoWSD.WebSocket ws);
    public List<NanoWSD.WebSocket> getSubscribers();
    
}
