package twg2.collections.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author TeamworkGuy2
 * @since 2016-10-26
 */
public final class GroupBy {

	private GroupBy() { throw new AssertionError("Cannot instantiate static class GroupBy"); }


	public static final <T, K> List<List<T>> groupByList(Iterable<T> iter, Function<T, K> extractKey, BiPredicate<K, K> compare) {
		return groupByList(iter.iterator(), extractKey, compare);
	}


	public static final <T, K> List<List<T>> groupByList(Iterator<T> iter, Function<T, K> extractKey, BiPredicate<K, K> compare) {
		Map<K, List<T>> groups = groupBy(iter, extractKey, compare);
		List<List<T>> res = new ArrayList<>();
		for(Map.Entry<K, List<T>> group : groups.entrySet()) {
			res.add(group.getValue());
		}
		return res;
	}


	public static final <T, K> Map<K, List<T>> groupBy(Iterable<T> iter, Function<T, K> extractKey, BiPredicate<K, K> compare) {
		return groupBy(iter.iterator(), extractKey, compare);
	}


	public static final <T, K> Map<K, List<T>> groupBy(Iterator<T> iter, Function<T, K> extractKey, BiPredicate<K, K> compare) {
		Map<K, List<T>> res = new HashMap<>();
		while(iter.hasNext()) {
			T val = iter.next();
			K key = extractKey.apply(val);
			if(key != null) {
				List<T> group = res.get(key);
				if(group == null) {
					res.put(key, group = new ArrayList<>());
				}
				group.add(val);
			}
		}
		return res;
	}

}
