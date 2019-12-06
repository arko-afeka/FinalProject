package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.afeka.project.server.WAFServer;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args )
    {
        Injector injector = Guice.createInjector(new WAFModule());
        WAFServer server = injector.getInstance(WAFServer.class);

        try {
            server.start();
            Thread.currentThread().wait();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
