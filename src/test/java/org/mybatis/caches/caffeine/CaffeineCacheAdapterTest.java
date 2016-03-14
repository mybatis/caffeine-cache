package org.mybatis.caches.caffeine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaffeineCacheAdapterTest {

	private static final String DEFAULT_ID = "Caffeine";

	private CaffeineCacheAdapter cache;

	@Before
	public void setup() {
		this.cache = new CaffeineCacheAdapter(DEFAULT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCache() {
		this.cache = new CaffeineCacheAdapter(null);
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
