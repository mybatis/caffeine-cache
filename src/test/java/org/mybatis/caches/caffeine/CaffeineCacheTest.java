/*
 *    Copyright 2016-2026 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.caffeine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.locks.ReadWriteLock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaffeineCacheTest {

  private static final String DEFAULT_ID = "Caffeine";

  private CaffeineCache cache;

  @BeforeEach
  public void setup() {
    this.cache = new CaffeineCache(DEFAULT_ID);
  }

  @Test
  public void blockNullKeyValuePair() {
    this.cache.putObject(null, null);
    assertThat(this.cache.getSize()).isEqualTo(0);
  }

  @Test
  public void blockNullKey() {
    this.cache.putObject(null, "foo");
    assertThat(this.cache.getSize()).isEqualTo(0);
  }

  @Test
  public void blockNullValue() {
    this.cache.putObject(1, null);
    assertThat(this.cache.getSize()).isEqualTo(0);
  }

  @Test
  public void readWriteLockShouldBeNull() {
    ReadWriteLock readWriteLock = this.cache.getReadWriteLock();
    assertThat(readWriteLock).isNull();
  }

  @Test
  public void shouldNotCreateCache() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      this.cache = new CaffeineCache(null);
    });
  }

  @Test
  public void shouldVerifyCacheId() {
    assertThat(this.cache.getId()).isEqualTo(DEFAULT_ID);
  }

  @Test
  public void shouldPersistObject() {
    this.cache.putObject(1, "foo");
    assertThat(this.cache.getObject(1)).isEqualTo("foo");
  }

  @Test
  public void shouldRemoveObject() {
    this.cache.putObject(1, "foo");

    assertThat(this.cache.getObject(1)).isEqualTo("foo");

    this.cache.removeObject(1);

    assertThat(this.cache.getObject(1)).isNull();
  }

  @Test
  public void shouldRemoveAllObjects() {
    this.cache.putObject(1, "foo");
    this.cache.putObject(2, "bar");

    assertThat(this.cache.getObject(1)).isEqualTo("foo");
    assertThat(this.cache.getObject(2)).isEqualTo("bar");

    this.cache.clear();

    assertThat(this.cache.getObject(1)).isNull();
    assertThat(this.cache.getObject(2)).isNull();
  }

  @Test
  public void shouldVerifySize() {
    this.cache.putObject(1, "foo");
    this.cache.putObject(2, "bar");

    assertThat(this.cache.getSize()).isEqualTo(2);
  }

}
