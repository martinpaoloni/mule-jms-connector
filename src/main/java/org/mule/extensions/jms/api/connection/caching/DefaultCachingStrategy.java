/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extensions.jms.api.connection.caching;

import static java.util.Optional.of;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;

import org.mule.extensions.jms.internal.connection.JmsCachingConnectionFactory;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.XAConnectionFactory;

/**
 * Default implementation of {@link CachingConfiguration} that not only enables caching
 * but also provides default values for all the configurable parameters
 *
 * @since 1.0
 */
@Alias("default-caching")
public class DefaultCachingStrategy implements CachingStrategy, CachingConfiguration {

  /**
   * Amount of {@link Session}s to cache
   */
  @Parameter
  @Optional
  @Expression(NOT_SUPPORTED)
  int sessionCacheSize = Integer.MAX_VALUE;

  /**
   * {@code true} if the {@link ConnectionFactory} should cache the {@link MessageProducer}s
   */
  @Parameter
  @Alias("cacheProducers")
  @Optional(defaultValue = "true")
  @Expression(NOT_SUPPORTED)
  boolean producersCache;

  /**
   * {@code true} if the {@link ConnectionFactory} should cache the {@link MessageConsumer}s
   */
  @Parameter
  @Alias("cacheConsumers")
  @Optional(defaultValue = "true")
  @Expression(NOT_SUPPORTED)
  boolean consumersCache;


  /**
   * @return the {@code sessionCacheSize}
   */
  @Override
  public int getSessionCacheSize() {
    return sessionCacheSize;
  }

  /**
   * @return {@code true} if {@link MessageProducer}s should be cached
   */
  @Override
  public boolean isProducersCache() {
    return producersCache;
  }

  /**
   * @return {@code true} if {@link MessageConsumer}s should be cached
   */
  @Override
  public boolean isConsumersCache() {
    return consumersCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean appliesTo(ConnectionFactory target) {
    return !(target instanceof XAConnectionFactory
        || target instanceof JmsCachingConnectionFactory
        || target instanceof CachingConnectionFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public java.util.Optional<CachingConfiguration> strategyConfiguration() {
    return of(this);
  }
}
