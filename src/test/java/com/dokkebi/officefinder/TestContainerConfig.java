package com.dokkebi.officefinder;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class TestContainerConfig implements BeforeAllCallback {

  private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
  private static final int REDIS_PORT = 6379;

  @ClassRule
  public static GenericContainer redis;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {

    redis = new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
        .withExposedPorts(REDIS_PORT)
        .withReuse(true);

    redis.start();
    System.setProperty("spring.data.redis.host", redis.getHost());
    log.info("host : {}", redis.getHost());
    System.setProperty("spring.data.redis.port", String.valueOf(redis.getMappedPort(REDIS_PORT)));
  }
}
