package org.mybatis.caches.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Cache adapter for Caffeine.
 *
 * @author Eddú Meléndez
 */
public final class CaffeineCacheAdapter implements Cache {

	private com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

	private String id;

	public CaffeineCacheAdapter(String id) {
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
		Object object = getObject(key);
		this.cache.invalidate(key);
		return object;
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
