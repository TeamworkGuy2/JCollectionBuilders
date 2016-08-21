package twg2.collections.builder.test;

import java.lang.annotation.RetentionPolicy;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.MapBuilder;

/**
 * @author TeamworkGuy2
 * @since 2014-12-16
 */
public final class MapBuilderTest {

	@Test
	public void mapBuilderTest() {
		@SuppressWarnings("unchecked")
		Entry<String, String>[] list1 = new Entry[] {
				new AbstractMap.SimpleImmutableEntry<>("__a", "alpha"),
				new AbstractMap.SimpleImmutableEntry<>("__b", "beta"),
				new AbstractMap.SimpleImmutableEntry<>("__c", "charlie"), // conflicting
				new AbstractMap.SimpleImmutableEntry<>("__c", "gamma") // conflicting
		};

		@SuppressWarnings("unchecked")
		Entry<String, String>[] expect1 = new Entry[] {
				new AbstractMap.SimpleImmutableEntry<>("__a", "alpha"),
				new AbstractMap.SimpleImmutableEntry<>("__b", "beta"),
				new AbstractMap.SimpleImmutableEntry<>("__c", "gamma") // last conflict overwrites previous
		};

		Map<String, String> map = MapBuilder.of(Arrays.asList(list1).iterator());
		List<Entry<String, String>> listA = new ArrayList<>(map.entrySet());
		List<Entry<String, String>> listB = Arrays.asList(expect1);
		// sort the lists since one came from a map
		Comparator<Entry<String, String>> comparator = (a, b) -> a.getKey().compareTo(b.getKey());
		Collections.sort(listA, comparator);
		Collections.sort(listB, comparator);
		Assert.assertEquals(listA, listB);
	}


	@Test
	public void concatMaps() {
		Map<String, String> all = new HashMap<>();
		all.put("1", "a1");
		all.put("2", "b2");
		all.put("3", "c3");
		all.put("4", "d4");
		all.put("5", "e5");

		Map<String, String> m1 = new HashMap<>();
		m1.put("1", "a1");
		m1.put("2", "b2");

		Map<String, String> m2 = new HashMap<>();
		m2.put("3", "c3");
		m2.put("4", "d4");

		Map<String, String> m3 = new HashMap<>();
		m3.put("5", "e5");

		Assert.assertEquals(all, MapBuilder.concat(m1, m2, m3));
	}


	@Test
	public void invertMap() {
		Map<String, String> src = new HashMap<>();
		src.put("1", "a1");
		src.put("2", "b2");
		src.put("3", "c3");

		Map<String, String> expect = new HashMap<>();
		expect.put("a1", "1");
		expect.put("b2", "2");
		expect.put("c3", "3");

		Map<String, String> res = MapBuilder.invert(src);
		Assert.assertEquals(expect, res);
	}


	@Test
	public void enumNames() {
		{
			Map<String, RetentionPolicy> expect = new HashMap<>();
			for(RetentionPolicy r : RetentionPolicy.values()) {
				expect.put(r.name(), r);
			}

			Map<String, RetentionPolicy> map = MapBuilder.immutableEnumNames(RetentionPolicy.class);
			Assert.assertEquals(expect, map);
		}

		{
			Map<String, RetentionPolicy> expect = new HashMap<>();
			for(RetentionPolicy r : RetentionPolicy.values()) {
				expect.put(r.name() + "@" + r.hashCode(), r);
			}

			Map<String, RetentionPolicy> map = MapBuilder.immutableEnumNames(RetentionPolicy.class, (r) -> r.name() + "@" + r.hashCode());
			Assert.assertEquals(expect, map);
		}
	}

}
