package twg2.collections.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/** Utility functions for {@link Map} operations, such as filtering,
 * transforming to and from {@link Collection}, etc.
 * @author TeamworkGuy2
 * @since 2015-4-28
 */
public class MapUtil {


	/** Convert a map using a mapping function.
	 * Note: the input map is reused by calling {@link Map#clear()} and then casting the
	 * input map type to the output map type and refilling it with the list of resulting transformed entries
	 * @param map the input map to convert
	 * @param transformer the function to transform the input map key-values
	 * @return the input {@code map} filled with the transformed values
	 */
	public static final <K, V, R, S> Map<R, S> mapReuse(Map<? extends K, ? extends V> map, BiFunction<K, V, Map.Entry<R, S>> transformer) {
		ArrayList<Map.Entry<R, S>> transformedKeyValues = new ArrayList<>(map.size());
		map.forEach((k, v) -> {
			Map.Entry<R, S> entry = transformer.apply(k, v);
			transformedKeyValues.add(entry);
		});
		map.clear();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<R, S> dst = (Map<R, S>)((Map)map);
		for(int i = 0, size = transformedKeyValues.size(); i < size; i++) {
			Map.Entry<R, S> entry = transformedKeyValues.get(i);
			dst.put(entry.getKey(), entry.getValue());
		}
		return dst;
	}


	/** Transforms a map of key-values into a new {@link HashMap}
	 * @see #map(Map, BiFunction, Map)
	 */
	public static final <K, V, R, S> Map<R, S> map(Map<? extends K, ? extends V> map, BiFunction<K, V, Map.Entry<R, S>> transformer) {
		return map(map, transformer, new HashMap<>(map.size()));
	}


	/** Transform a map using a mapping function and store the resulting entries
	 * in a given destination map
	 * @param map the input map to convert
	 * @param transformer the function to transform the input map key-values
	 * @param dst the destination map to store the transformed entries in
	 * @return the input {@code dst} map filled with the transformed values
	 */
	public static final <K, V, R, S> Map<R, S> map(Map<? extends K, ? extends V> map, BiFunction<K, V, Map.Entry<R, S>> transformer, Map<R, S> dst) {
		map.forEach((k, v) -> {
			Map.Entry<R, S> entry = transformer.apply(k, v);
			dst.put(entry.getKey(), entry.getValue());
		});
		return dst;
	}


	/** Filter a map of key-values into a new {@link HashMap}
	 * @see #map(Map, BiFunction, Map)
	 */
	public static final <K, V> Map<K, V> filter(Map<? extends K, ? extends V> map, BiPredicate<K, V> filter) {
		return filter(map, filter, new HashMap<>(map.size()));
	}


	/** Filter a map and store the resulting entries in a given destination map
	 * @param map the input map to convert
	 * @param filter the function to filter the {@code map} key-values
	 * @param dst the destination map to store the transformed entries in
	 * @return the input {@code dst} map filled with the transformed values
	 */
	public static final <K, V> Map<K, V> filter(Map<? extends K, ? extends V> map, BiPredicate<K, V> filter, Map<K, V> dst) {
		map.forEach((k, v) -> {
			if(filter.test(k, v)) {
				dst.put(k, v);
			}
		});
		return dst;
	}


	/** Filter and transforms a map of key-values into a new {@link HashMap}
	 * @see #filterMap(Map, BiPredicate, BiFunction, Map)
	 */
	public static final <K, V, R, S> Map<R, S> filterMap(Map<? extends K, ? extends V> map, BiPredicate<K, V> filter, BiFunction<K, V, Map.Entry<R, S>> transformer) {
		return filterMap(map, filter, transformer, new HashMap<>(map.size()));
	}


	/** Filter and transform a map using a filter and mapping function
	 * and store the resulting entries in a given destination map
	 * @param map the input map to convert
	 * @param filter the function to filter the {@code map} key-values
	 * @param transformer the function to transform the input map key-values
	 * @param dst the destination map to store the transformed entries in
	 * @return the input {@code dst} map filled with the transformed values
	 */
	public static final <K, V, R, S> Map<R, S> filterMap(Map<? extends K, ? extends V> map, BiPredicate<K, V> filter, BiFunction<K, V, Map.Entry<R, S>> transformer, Map<R, S> dst) {
		map.forEach((k, v) -> {
			if(filter.test(k, v)) {
				Map.Entry<R, S> entry = transformer.apply(k, v);
				dst.put(entry.getKey(), entry.getValue());
			}
		});
		return dst;
	}


	/**
	 * @see #map(Iterator, Function, Map)
	 */
	public static final <E, K, V> Map<K, V> map(Iterator<E> valueIter, Function<E, Map.Entry<K, V>> transformer) {
		return map(valueIter, transformer, new HashMap<>());
	}


	/** Map an iterator to a key-value map using a {@code transformer} function
	 * @param valueIter the iterator to get values from
	 * @param transformer the function that takes a value and converts it to a key-value pair
	 * @param dst the destination map in which to store the key-value results of applying the {@code transformer}
	 * function to each of the {@code values}
	 * @return the {@code dst} map
	 */
	public static final <E, K, V> Map<K, V> map(Iterator<E> valueIter, Function<E, Map.Entry<K, V>> transformer, Map<K, V> dst) {
		while(valueIter.hasNext()) {
			E val = valueIter.next();
			Map.Entry<K, V> entry = transformer.apply(val);
			dst.put(entry.getKey(), entry.getValue());
		}
		return dst;
	}


	/**
	 * @see #map(Iterable, Function, Map)
	 */
	public static final <E, K, V> Map<K, V> map(Iterable<E> values, Function<E, Map.Entry<K, V>> transformer) {
		return map(values, transformer, new HashMap<>()); 
	}


	/** Map a collection of values to a key-value map using a {@code transformer} function
	 * @param values the collection of values to convert
	 * @param transformer the function that takes a value and converts it to a key-value pair
	 * @param dst the destination map in which to store the key-value results of applying the {@code transformer}
	 * function to each of the {@code values}
	 * @return the {@code dst} map
	 */
	public static final <E, K, V> Map<K, V> map(Iterable<E> values, Function<E, Map.Entry<K, V>> transformer, Map<K, V> dst) {
		if(values instanceof RandomAccess && values instanceof List) {
			List<E> valuesList = (List<E>)values;
			for(int i = 0, size = valuesList.size(); i < size; i++) {
				Map.Entry<K, V> entry = transformer.apply(valuesList.get(i));
				dst.put(entry.getKey(), entry.getValue());
			}
		}
		else {
			for(E val : values) {
				Map.Entry<K, V> entry = transformer.apply(val);
				dst.put(entry.getKey(), entry.getValue());
			}
		}
		return dst;
	}


	/**
	 * @see #map(Function, Map, Object...)
	 */
	@SafeVarargs
	public static final <E, K, V> Map<K, V> map(Function<E, Map.Entry<K, V>> transformer, E... values) {
		return map(transformer, new HashMap<>(values.length), values);
	}


	/** Map an array of values to a key-value map using a {@code transformer} function
	 * @param transformer the function that takes a value and converts it to a key-value pair
	 * @param values the array of values to convert
	 * @param dst the destination map in which to store the key-value results of applying the {@code transformer}
	 * function to each of the {@code values}
	 * @return the {@code dst} map
	 */
	@SafeVarargs
	public static final <E, K, V> Map<K, V> map(Function<E, Map.Entry<K, V>> transformer, Map<K, V> dst, E... values) {
		Map<K, V> resMap = new HashMap<>(values.length);
		for(E val : values) {
			Map.Entry<K, V> entry = transformer.apply(val);
			resMap.put(entry.getKey(), entry.getValue());
		}
		return resMap;
	}


}
