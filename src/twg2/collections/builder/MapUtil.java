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
public final class MapUtil {

	private MapUtil() { throw new AssertionError("cannot instantiate static class MapUtil"); }


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
		return filter(map, filter, new HashMap<>());
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
		return filterMap(map, filter, transformer, new HashMap<>());
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


	/** Returns a transformed map where all of the new keys are ensured to be unique (i.e. the size of the original and returned maps will be the same).
	 * An error is thrown if two new keys collide. The map's values are transfered without modification to the new map
	 * @param values the map of values
	 * @param keyMapper the function to transform keys
	 * @return the map of transformed keys to original values
	 * @see #mapCheckNewKeyUniqueness(Map, Function, Function, boolean, boolean)
	 */
	public static final <K, V, R> Map<R, V> mapRequireUnique(Map<K, V> values, Function<? super K, R> keyMapper) {
		return mapCheckNewKeyUniqueness(values, keyMapper, Function.identity(), false, true);
	}


	/** Returns a transformed map where all of the new keys are ensured to be unique (i.e. the size of the original and returned maps will be the same).
	 * An error is thrown if two new keys collide
	 * @param values the map of values
	 * @param keyMapper the function to transform keys
	 * @param valueMapper the function to transform values
	 * @return the map of transformed keys to original values
	 * @see #mapCheckNewKeyUniqueness(Map, Function, Function, boolean, boolean)
	 */
	public static final <K, V, R, S> Map<R, S> mapRequireUnique(Map<K, V> values, Function<? super K, R> keyMapper, Function<? super V, S> valueMapper) {
		return mapCheckNewKeyUniqueness(values, keyMapper, valueMapper, false, true);
	}


	/** Returns a map of transformed keys and values.
	 * @param values the map of values
	 * @param keyMapper the function to transform keys
	 * @param valueMapper the function to transform values
	 * @param ifDuplicateKeyKeepFirst true to keep the first key if two new keys are duplicates, false to overwrite new keys with duplicate new keys as the map is transformed
	 * (the order of map traversal is not guaranteed so the order in which duplicate entries overwrite each other is not guaranteed)
	 * @param throwIfDuplicateKeys true to throw an error if duplicate new keys are encountered, this flag overrides {@code ifDuplicateKeyKeepFirst}
	 * @return the map of transformed keys to original values
	 */
	public static final <K, V, R, S> Map<R, S> mapCheckNewKeyUniqueness(Map<K, V> values, Function<? super K, R> keyMapper, Function<? super V, S> valueMapper,
			boolean ifDuplicateKeyKeepFirst, boolean throwIfDuplicateKeys) {
		Map<R, S> resMap = new HashMap<>();
		// standard mapping operation
		if(!ifDuplicateKeyKeepFirst && !throwIfDuplicateKeys) {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				resMap.put(keyMapper.apply(entry.getKey()), valueMapper.apply(entry.getValue()));
			}
		}

		// keep first if duplicates
		else if(ifDuplicateKeyKeepFirst & !throwIfDuplicateKeys) {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				R newKey = keyMapper.apply(entry.getKey());
				if(!resMap.containsKey(newKey)) {
					resMap.put(newKey, valueMapper.apply(entry.getValue()));
				}
			}
		}

		// throw if duplicates
		else {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				R newKey = keyMapper.apply(entry.getKey());
				if(resMap.containsKey(newKey)) {
					// find the original key for descriptive error
					for(Map.Entry<K, V> original : values.entrySet()) {
						if(keyMapper.apply(original.getKey()).equals(newKey)) {
							throw new IllegalArgumentException("duplicate key: '" + newKey + "' first: " + original.getKey() + ", second: " + entry);
						}
					}
				}
				resMap.put(newKey, valueMapper.apply(entry.getValue()));
			}
		}
		return resMap;
	}


	/** Returns a transformed map where all of the new keys are ensured to be unique (i.e. the size of the original and returned maps will be the same).
	 * An error is thrown if two new keys collide. The map's values are transfered without modification to the new map
	 * @param values the map of values
	 * @param entryMapper the function to transform keys
	 * @return the map of transformed keys to original values
	 * @see #mapCheckNewKeyUniqueness(Map, BiFunction, boolean, boolean)
	 */
	public static final <K, V, R, S> Map<R, S> mapRequireUnique(Map<K, V> values, BiFunction<? super K, ? super V, ? extends Map.Entry<R, S>> entryMapper) {
		return mapCheckNewKeyUniqueness(values, entryMapper, false, true);
	}


	/** Returns a map of transformed keys and values.
	 * @param values the map of values
	 * @param entryMapper the function to transform keys and values
	 * @param ifDuplicateKeyKeepFirst true to keep the first key if two new keys are duplicates, false to overwrite new keys with duplicate new keys as the map is transformed
	 * (the order of map traversal is not guaranteed so the order in which duplicate entries overwrite each other is not guaranteed)
	 * @param throwIfDuplicateKeys true to throw an error if duplicate new keys are encountered, this flag overrides {@code ifDuplicateKeyKeepFirst}
	 * @return the map of transformed keys to original values
	 */
	public static final <K, V, R, S> Map<R, S> mapCheckNewKeyUniqueness(Map<K, V> values, BiFunction<? super K, ? super V, ? extends Map.Entry<R, S>> entryMapper,
			boolean ifDuplicateKeyKeepFirst, boolean throwIfDuplicateKeys) {
		Map<R, S> resMap = new HashMap<>();
		// standard mapping operation
		if(!ifDuplicateKeyKeepFirst && !throwIfDuplicateKeys) {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				Map.Entry<R, S> newEntry = entryMapper.apply(entry.getKey(), entry.getValue());
				resMap.put(newEntry.getKey(), newEntry.getValue());
			}
		}

		// keep first if duplicates
		else if(ifDuplicateKeyKeepFirst & !throwIfDuplicateKeys) {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				Map.Entry<R, S> newEntry = entryMapper.apply(entry.getKey(), entry.getValue());
				if(!resMap.containsKey(newEntry.getKey())) {
					resMap.put(newEntry.getKey(), newEntry.getValue());
				}
			}
		}

		// throw if duplicates
		else {
			for(Map.Entry<K, V> entry : values.entrySet()) {
				Map.Entry<R, S> newEntry = entryMapper.apply(entry.getKey(), entry.getValue());
				R newKey = newEntry.getKey();
				if(resMap.containsKey(newKey)) {
					// find the original key for descriptive error, we inefficiently run the entryMapper over the map's entries
					// again, however, an error is about to be thrown so the overhead won't affect normal program execution
					for(Map.Entry<K, V> original : values.entrySet()) {
						if(entryMapper.apply(original.getKey(), original.getValue()).getKey().equals(newKey)) {
							throw new IllegalArgumentException("duplicate key: '" + newKey + "' first: " + original.getKey() + ", second: " + entry);
						}
					}
				}
				resMap.put(newKey, newEntry.getValue());
			}
		}
		return resMap;
	}

}
