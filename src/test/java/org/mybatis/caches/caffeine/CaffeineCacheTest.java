/**
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.mybatis.caches.caffeine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaffeineCacheTest {

  private static final String DEFAULT_ID = "Caffeine";

  private CaffeineCache cache;

  @Before
  public void setup() {
    this.cache = new CaffeineCache(DEFAULT_ID);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotCreateCache() {
    this.cache = new CaffeineCache(null);
  }

  @Test
  public void shouldVerifyCacheId() {
    assertEquals(DEFAULT_ID, this.cache.getId());
  }

  @Test
  public void shouldPersistObject() {
    this.cache.putObject(1, "foo");
    assertEquals("foo", this.cache.getObject(1));
  }

  @Test
  public void shouldRemoveObject() {
    this.cache.putObject(1, "foo");

    assertEquals("foo", this.cache.getObject(1));

    this.cache.removeObject(1);

    assertNull(this.cache.getObject(1));
  }

  @Test
  public void shouldRemoveAllObjects() {
    this.cache.putObject(1, "foo");
    this.cache.putObject(2, "bar");

    assertEquals("foo", this.cache.getObject(1));
    assertEquals("bar", this.cache.getObject(2));

    this.cache.clear();

    assertNull(this.cache.getObject(1));
    assertNull(this.cache.getObject(2));
  }

  @Test
  public void shouldVerifySize() {
    this.cache.putObject(1, "foo");
    this.cache.putObject(2, "bar");

    assertEquals(2, this.cache.getSize());
  }

}
