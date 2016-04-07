/**
 *    Copyright 2016 the original author or authors.
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

import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Cache adapter for Caffeine.
 *
 * @author Eddú Meléndez
 */
public final class CaffeineCache implements Cache {

  private com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

  private String id;

  public CaffeineCache(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Cache instances require an ID");
    }

    this.cache = Caffeine.newBuilder().build();
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void putObject(Object key, Object value) {
    this.cache.put(key, value);
  }

  @Override
  public Object getObject(Object key) {
    return this.cache.getIfPresent(key);
  }

  @Override
  public Object removeObject(Object key) {
    return this.cache.asMap().remove(key);
  }

  @Override
  public void clear() {
    this.cache.invalidateAll();
  }

  @Override
  public int getSize() {
    return (int) this.cache.estimatedSize();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return null;
  }

}
