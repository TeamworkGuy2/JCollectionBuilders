package twg2.collections.builder.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.GroupBy;
import twg2.collections.builder.MapUtil;

/**
 * @author TeamworkGuy2
 * @since 2016-10-26
 */
public class GroupByTest {
	private List<String> vals = Arrays.asList("abc", "at", "alpha", "beta", "blue", "back", "charlie", "char");


	@Test
	public void groupByListTest() {
		List<List<String>> res = GroupBy.groupByList(vals, (v) -> v.charAt(0), (a, b) -> a == b);
		Map<Character, List<String>> groups = MapUtil.map(res, (g) -> pair(g.get(0).charAt(0), g));

		Assert.assertEquals(Arrays.asList('a', 'b', 'c'), listSorted(groups.keySet()));
		Assert.assertEquals(Arrays.asList("abc", "alpha", "at"), listSorted(groups.get('a')));
		Assert.assertEquals(Arrays.asList("back", "beta", "blue"), listSorted(groups.get('b')));
		Assert.assertEquals(Arrays.asList("char", "charlie"), listSorted(groups.get('c')));
	}


	@Test
	public void groupByTest() {
		Map<Character, List<String>> groups = GroupBy.groupBy(vals, (v) -> v.charAt(0), (a, b) -> a == b);

		Assert.assertEquals(Arrays.asList('a', 'b', 'c'), listSorted(groups.keySet()));
		Assert.assertEquals(Arrays.asList("abc", "alpha", "at"), listSorted(groups.get('a')));
		Assert.assertEquals(Arrays.asList("back", "beta", "blue"), listSorted(groups.get('b')));
		Assert.assertEquals(Arrays.asList("char", "charlie"), listSorted(groups.get('c')));
	}


	@Test
	public void groupByTest2() {
		Map<Character, List<String>> groups = GroupBy.groupBy(Arrays.asList("aa", "ab", "bb", "bc", "", null), (v) -> v != null && v.length() > 0 ? v.charAt(0) : null, (a, b) -> a == b);

		Assert.assertEquals(Arrays.asList('a', 'b'), listSorted(groups.keySet()));
		Assert.assertEquals(Arrays.asList("aa", "ab"), listSorted(groups.get('a')));
		Assert.assertEquals(Arrays.asList("bb", "bc"), listSorted(groups.get('b')));
	}


	private static final <K, V> Entry<K, V> pair(K k, V v) {
		return new AbstractMap.SimpleImmutableEntry<>(k, v);
	}


	private static final <T extends Comparable<T>> List<T> listSorted(Iterable<T> ts) {
		List<T> res = new ArrayList<>();
		for(T t : ts) {
			res.add(t);
		}
		Collections.sort(res);
		return res;
	}

}
