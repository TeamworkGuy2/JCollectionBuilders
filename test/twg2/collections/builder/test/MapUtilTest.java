package twg2.collections.builder.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import twg2.collections.builder.MapUtil;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2017-06-24
 */
public class MapUtilTest {
	Map<String, String> map1 = new HashMap<>();
	Map<String, String> duplicates1 = new LinkedHashMap<>();
	List<Entry<String, String>> deduplicatesFirst = Arrays.asList(pair("A", "Alpha"), pair("B", "Beta"));
	List<Entry<String, String>> deduplicatesLast = Arrays.asList(pair("A", "Alpha"), pair("B", "2"));

	Comparator<Entry<String, String>> strComparator = (a, b) -> a.getKey().compareTo(b.getKey());

	{
		map1.put("A", "Alpha");
		map1.put("B", "Beta");
		map1.put("C", "Charlie");
		map1.put("D", "Delta");

		duplicates1.put("A1", "Alpha");
		duplicates1.put("B1", "Beta");
		duplicates1.put("B2", "2");
	}


	@Test
	public void filter() {
		Map<String, String> res = MapUtil.filter(map1, (k, v) -> k.compareTo("C") < 0);
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), Arrays.asList(pair("A", "Alpha"), pair("B", "Beta")), strComparator);
	}


	@Test
	public void filterMap() {
		Map<String, String> res = MapUtil.filterMap(map1, (k, v) -> k.compareTo("C") < 0, (k, v) -> pair("-" + k, v.toLowerCase()));
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), Arrays.asList(pair("-A", "alpha"), pair("-B", "beta")), strComparator);
	}


	@Test
	public void checkNewKeyUniquenessEntries() {
		Map<String, String> res = MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k, v) -> pair(k.substring(0, 1), v), true, false);
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), deduplicatesFirst, strComparator);

		res = MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k, v) -> pair(k.substring(0, 1), v), false, false);
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), deduplicatesLast, strComparator);

		CheckTask.assertException(() -> MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k, v) -> pair(k.substring(0, 1), v), true, true));
	}


	@Test
	public void checkNewKeyUniquenessKeyValues() {
		Map<String, String> res = MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k) -> k.substring(0, 1), (v) -> v, true, false);
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), deduplicatesFirst, strComparator);

		res = MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k) -> k.substring(0, 1), (v) -> v, false, false);
		MapBuilderTest.entriesEqual(new ArrayList<>(res.entrySet()), deduplicatesLast, strComparator);

		CheckTask.assertException(() -> MapUtil.mapCheckNewKeyUniqueness(duplicates1, (k) -> k.substring(0, 1), (v) -> v, true, true));
	}


	@Test
	public void map() {
		Map<String, String> res1 = MapUtil.map(map1.entrySet(), (kv) -> pair(kv.getKey(), kv.getValue()));
		MapBuilderTest.entriesEqual(new ArrayList<>(res1.entrySet()), new ArrayList<>(map1.entrySet()), strComparator);
	}


	private static final <K, V> Entry<K, V> pair(K k, V v) {
		return new AbstractMap.SimpleImmutableEntry<>(k, v);
	}

}
