/**
 *    Copyright 2016-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.caffeine;

import static org.assertj.core.api.Assertions.assertThat;

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
  public void shouldNotCreateCache() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      this.cache = new CaffeineCache(null);
    });
  }

  @Test
  public void shouldVerifyCacheId() {
    assertThat(DEFAULT_ID).isEqualTo(this.cache.getId());
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
