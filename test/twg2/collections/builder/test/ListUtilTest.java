package twg2.collections.builder.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.ListUtil;

/**
 * @author TeamworkGuy2
 * @since 2016-3-1
 */
public class ListUtilTest {

	@Test
	public void combineTest() {
		List<String> col1 = Arrays.asList("t1", "t2", "t3", "t4", "t5");
		List<Boolean> col2 = Arrays.asList(true, false, false, true, true);

		List<Entry<String, Boolean>> res = ListUtil.combine(col1, col2, AbstractMap.SimpleImmutableEntry<String, Boolean>::new);

		Assert.assertEquals(Arrays.asList(
			pair("t1", true),
			pair("t2", false),
			pair("t3", false),
			pair("t4", true),
			pair("t5", true)
		), res);
	}


	@Test
	public void toArrayTest() {
		List<List<String>> inputs = Arrays.asList(
			Arrays.asList("a", "b", "c"),
			Arrays.asList("a")
		);

		List<String[]> expected = Arrays.asList(
			new String[] { "a", "b", "c" },
			new String[] { "a" }
		);

		for(int i = 0, size = inputs.size(); i < size; i++) {
			String[] res = ListUtil.toArray(inputs.get(i));
			Assert.assertArrayEquals(expected.get(i), res);

			res = ListUtil.toArray(new LinkedList<>(inputs.get(i)));
			Assert.assertArrayEquals(expected.get(i), res);
		}
	}


	@Test
	public void mapTest() {
		List<List<String>> inputs = Arrays.asList(
			Arrays.asList("a", "bb", "ccc"),
			Arrays.asList("dddd")
		);

		List<List<Entry<Integer, String>>> expected = Arrays.asList(
			Arrays.asList(pair(1, "a"), pair(2, "bb"), pair(3, "ccc")),
			Arrays.asList(pair(4, "dddd"))
		);

		for(int i = 0, size = inputs.size(); i < size; i++) {
			// list
			List<Entry<Integer, String>> res = ListUtil.map(inputs.get(i), (s) -> pair(s.length(), s));
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.map(inputs.get(i), (s) -> pair(s.length(), s), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// collection
			res = ListUtil.map((Collection<String>)new LinkedList<>(inputs.get(i)), (s) -> pair(s.length(), s));
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.map((Collection<String>)new LinkedList<>(inputs.get(i)), (s) -> pair(s.length(), s), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// iterable
			res = ListUtil.map((Iterable<String>)inputs.get(i), (s) -> pair(s.length(), s));
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.map((Iterable<String>)inputs.get(i), (s) -> pair(s.length(), s), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// iterator
			res = ListUtil.map(inputs.get(i).iterator(), (s) -> pair(s.length(), s));
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.map(inputs.get(i).iterator(), (s) -> pair(s.length(), s), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// stream
			res = ListUtil.map(inputs.get(i).stream(), (s) -> pair(s.length(), s));
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.map(inputs.get(i).stream(), (s) -> pair(s.length(), s), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);
		}
	}


	@Test
	public void filterTest() {
		List<List<Entry<Integer, String>>> inputs = Arrays.asList(
			Arrays.asList(pair(1, "a"), pair(2, "bb"), pair(3, "ccc"), pair(4, "dddd")),
			Arrays.asList(pair(5, "eeeee"))
		);

		List<List<Entry<Integer, String>>> expected = Arrays.asList(
			Arrays.asList(pair(2, "bb"), pair(4, "dddd")),
			Arrays.asList()
		);

		for(int i = 0, size = inputs.size(); i < size; i++) {
			// list
			List<Entry<Integer, String>> res = ListUtil.filter(inputs.get(i), (s) -> s.getKey() % 2 == 0);
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filter(inputs.get(i), (s) -> s.getKey() % 2 == 0, new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// collection
			res = ListUtil.filter((Collection<Entry<Integer, String>>)new LinkedList<>(inputs.get(i)), (s) -> s.getKey() % 2 == 0);
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filter((Collection<Entry<Integer, String>>)new LinkedList<>(inputs.get(i)), (s) -> s.getKey() % 2 == 0, new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// iterable
			res = ListUtil.filter((Iterable<Entry<Integer, String>>)inputs.get(i), (s) -> s.getKey() % 2 == 0);
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filter((Iterable<Entry<Integer, String>>)inputs.get(i), (s) -> s.getKey() % 2 == 0, new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// iterator
			res = ListUtil.filter(inputs.get(i).iterator(), (s) -> s.getKey() % 2 == 0);
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filter(inputs.get(i).iterator(), (s) -> s.getKey() % 2 == 0, new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			// stream
			res = ListUtil.filter(inputs.get(i).stream(), (s) -> s.getKey() % 2 == 0);
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filter(inputs.get(i).stream(), (s) -> s.getKey() % 2 == 0, new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);
		}
	}


	@Test
	public void filterMapTest() {
		List<List<Entry<Integer, String>>> inputs = Arrays.asList(
			Arrays.asList(pair(1, "a"), pair(2, "bb"), pair(3, "ccc"), pair(4, "dddd")),
			Arrays.asList(pair(5, "eeeee"))
		);

		List<List<Integer>> expected = Arrays.asList(
			Arrays.asList(2, 4),
			Arrays.asList()
		);

		for(int i = 0, size = inputs.size(); i < size; i++) {
			// collection
			List<Integer> res = ListUtil.filterMap(inputs.get(i), (s) -> s.getKey() % 2 == 0, (s) -> s.getKey());
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filterMap(inputs.get(i), (s) -> s.getKey() % 2 == 0, (s) -> s.getKey(), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);

			res = ListUtil.filterMap(new LinkedList<>(inputs.get(i)), (s) -> s.getKey() % 2 == 0, (s) -> s.getKey(), new ArrayList<>());
			Assert.assertEquals(expected.get(i), res);
		}
	}


	private static final <K, V> Entry<K, V> pair(K k, V v) {
		return new AbstractMap.SimpleImmutableEntry<>(k, v);
	}

}
