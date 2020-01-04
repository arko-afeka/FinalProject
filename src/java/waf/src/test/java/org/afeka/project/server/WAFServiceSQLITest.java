/*
 * Copyright 2016 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.afeka.project.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.afeka.project.WAFModule;
import org.afeka.project.waf.api.HTTPRequest;
import org.afeka.project.waf.api.Ping;
import org.afeka.project.waf.api.Pong;
import org.afeka.project.waf.api.ServerAPIGrpc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WAFServiceSQLITest {
  @Rule public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  private ServerAPIGrpc.ServerAPIBlockingStub stub;
  private Injector injector = Guice.createInjector(new WAFModule());

  @Before
  public void registerServer() throws Exception {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();

    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(
        InProcessServerBuilder.forName(serverName)
            .directExecutor()
            .addService(injector.getInstance(WAFService.class))
            .build()
            .start());

    stub =
        ServerAPIGrpc.newBlockingStub(
            // Create a client channel and register for automatic graceful shutdown.
            grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName).directExecutor().build()));
  }

  @Test
  public void testHTTPRequest() throws Exception {
    stub.isValidRequest(
        HTTPRequest.newBuilder()
            .setData(
                "GET /pub/WWW/TheProject.html?test=%27%20OR%201%3D1-- HTTP/1.1\r\n"
                    + "Host: www.w3.org\r\n\r\n")
            .build());
  }

  @Test
  public void pingPongTest() throws Exception {

    Pong pong = stub.ping(Ping.newBuilder().build());

    assertEquals(pong, Pong.newBuilder().build());
  }
}
