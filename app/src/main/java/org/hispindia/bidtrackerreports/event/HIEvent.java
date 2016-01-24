package org.hispindia.bidtrackerreports.event;

import com.squareup.otto.Bus;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIEvent {
    private static Bus bus = new Bus();

    public static void register(Object target) {
        bus.register(target);
    }

    public static void unregister(Object target) {
        bus.unregister(target);
    }

    public static void post(Object event) {
        bus.post(event);
    }
}
