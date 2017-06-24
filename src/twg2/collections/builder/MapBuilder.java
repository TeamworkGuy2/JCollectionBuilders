package twg2.collections.builder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.RandomAccess;
import java.util.function.Function;

/** A utility class for building {@link Map Maps}
 * @author TeamworkGuy2
 * @since 2014-10-31
 */
public final class MapBuilder {

	private MapBuilder() { throw new AssertionError("cannot instantiate static class MapBuilder"); }


	/** Creates an immutable map containing the list of entries
	 * @see #immutable(java.util.Map.Entry...)
	 */
	@SafeVarargs
	public static final <K, V> Map<K, V> of(Map.Entry<K, V>... entries) {
		return immutable(entries);
	}


	/** Creates an immutable map containing the entries from an iterable data set
	 * @see #immutable(Iterable)
	 */
	public static final <K, V> Map<K, V> of(Iterable<? extends Map.Entry<K, V>> iterable) {
		return immutable(iterable);
	}


	/** Creates an immutable map containing the list of entries
	 * @see #immutable(Iterator)
	 */
	public static final <K, V> Map<K, V> of(Iterator<? extends Map.Entry<K, V>> entryIter) {
		return immutable(entryIter);
	}


	/** Creates an immutable map containing the list of entries
	 * @param entries the list entries to include in the map
	 * @return a new, immutable, map containing the list of input entries
	 */
	@SafeVarargs
	public static final <K, V> Map<K, V> immutable(Map.Entry<K, V>... entries) {
		return Collections.unmodifiableMap(mutable(entries));
	}


	/** Creates an immutable map containing the entries from the iterator data set provided
	 * @param iterable an entry iterator containing the values to include in the map
	 * @return a new, immutable, map containing the input iterable's entries
	 */
	public static final <K, V> Map<K, V> immutable(Iterable<? extends Map.Entry<K, V>> iterable) {
		return Collections.unmodifiableMap(mutable(iterable.iterator()));
	}


	/** Creates an immutable map containing the remaining entries from the iterator provided
	 * @param entryIter an entry iterator containing the values to include in the map
	 * @return a new, immutable, map containing the input iterator's entries
	 */
	public static final <K, V> Map<K, V> immutable(Iterator<? extends Map.Entry<K, V>> entryIter) {
		return Collections.unmodifiableMap(mutable(entryIter));
	}


	/** Creates a mutable map containing the list of entries
	 * @param entries the list entries to include in the map
	 * @return a new, mutable, map containing the list of input entries
	 */
	@SafeVarargs
	public static final <K, V> Map<K, V> mutable(Map.Entry<K, V>... entries) {
		Map<K, V> entryMap = new HashMap<>(entries.length);
		for(Map.Entry<K, V> entry : entries) {
			entryMap.put(entry.getKey(), entry.getValue());
		}
		return entryMap;
	}



	/** Creates a mutable map containing the iterable set of entries
	 * @param entries the iterable entries to include in the map
	 * @return a new, mutable, map containing the iterable set of input entries
	 */
	public static final <K, V> Map<K, V> mutable(Iterable<? extends Map.Entry<K, V>> entries) {
		Map<K, V> entryMap = new HashMap<>();
		if(entries instanceof List && entries instanceof RandomAccess) {
			List<? extends Map.Entry<K, V>> entryList = (List<? extends Map.Entry<K, V>>)entries;
			for(int i = 0, size= entryList.size(); i < size; i++) {
				Map.Entry<K, V> entry = entryList.get(i);
				entryMap.put(entry.getKey(), entry.getValue());
			}
		}
		else {
			for(Map.Entry<K, V> entry : entries) {
				entryMap.put(entry.getKey(), entry.getValue());
			}
		}
		return entryMap;
	}


	public static final <K, V> Map<K, V> mutable(Collection<K> col1, Collection<V> col2, boolean errorIfDuplicateKey) {
		int size1 = col1.size();
		int size2 = col2.size();
		if(size1 != size2) {
			throw new IllegalArgumentException("cannot combine collections with different lengths, collection 1 has " + size1 + " elements, collection 2 has " + size2);
		}

		return mutable(col1.iterator(), col2.iterator(), errorIfDuplicateKey, new HashMap<>());
	}


	public static final <K, V> Map<K, V> mutable(Iterator<? extends K> iterKeys, Iterator<? extends V> iterVals, boolean errorIfDuplicateKey, Map<K, V> dst) {
		int i = 0;
		while(iterKeys.hasNext() && iterVals.hasNext()) {
			K key = iterKeys.next();
			V value = iterVals.next();
			if(errorIfDuplicateKey && dst.containsKey(key)) {
				throw new IllegalArgumentException("duplicate key encountered while building map: " + key);
			}
			dst.put(key, value);
			i++;
		}

		boolean hasKey = iterKeys.hasNext();
		if(hasKey || iterVals.hasNext()) {
			throw new IllegalArgumentException((!hasKey ? "keys" : "values") + " iterator had fewer elements (" + i + ") than the " + (hasKey ? "keys" : "values") + " iterator");
		}

		return dst;
	}


	/** Creates a mutable map containing the remaining entries from the iterator provided
	 * @param entryIter an entry iterator containing the values to include in the map
	 * @return a new, mutable, map containing the input iterator's entries
	 */
	public static final <K, V> Map<K, V> mutable(Iterator<? extends Map.Entry<K, V>> entryIter) {
		Map<K, V> entryMap = new HashMap<>();
		while(entryIter.hasNext()) {
			Map.Entry<K, V> entry = entryIter.next();
			entryMap.put(entry.getKey(), entry.getValue());
		}
		return entryMap;
	}


	// ==== Enums to map ====

	/** Creates an immutable mapping of enum constant names to enum constants
	 * @param enumClass an {@link Enum} class
	 * @return a map of the enum constant names to enums constants from the specified enum
	 */
	public static final <E extends Enum<E>> Map<String, E> immutableEnumNames(Class<E> enumClass) {
		return Collections.unmodifiableMap(mutableEnumNames(enumClass));
	}


	/** Creates a mutable mapping of enum constant names to enum constants
	 * @param enumClass an {@link Enum} class
	 * @return a map of the enum constant names to enums constants from the specified enum
	 */
	public static final <E extends Enum<E>> Map<String, E> mutableEnumNames(Class<E> enumClass) {
		E[] enums = enumClass.getEnumConstants();
		Map<String, E> entryMap = new HashMap<>();
		for(E enumI : enums) {
			entryMap.put(enumI.name(), enumI);
		}
		return entryMap;
	}


	/** @see #mutableEnumNames(Class, Function)
	 */
	public static final <E extends Enum<E>, R> Map<R, E> immutableEnumNames(Class<E> enumClass, Function<E, R> enumToKey) {
		return Collections.unmodifiableMap(mutableEnumNames(enumClass, enumToKey));
	}


	/** Creates a mutable mapping of a mapped value for each enum name to the enum's constants
	 * @param enumClass an {@link Enum} class
	 * @param enumToKey function that converts an enum value to another value which is stored as the key for the enum value in the returned map
	 * @return a map of the enum constant names to enums constants from the specified enum
	 */
	public static final <E extends Enum<E>, R> Map<R, E> mutableEnumNames(Class<E> enumClass, Function<E, R> enumToKey) {
		E[] enums = enumClass.getEnumConstants();
		Map<R, E> entryMap = new HashMap<>();
		for(E enumI : enums) {
			entryMap.put(enumToKey.apply(enumI), enumI);
		}
		return entryMap;
	}


	/** Combine a list of maps into one map according to the {@link Map#putAll(Map)} contract.<br>
	 * Duplicate keys are overwritten. Keys from the last map of {@code maps} taking
	 * precedence over keys from the second to last map and keys from the second to last
	 * map taking precedence over keys from the third to last map, etc.
	 * @param maps the list of maps to combine
	 * @return a single map containing all of the non-duplicate key-value pairs from all of the {@code maps}
	 */
	@SafeVarargs
	public static final <K, V> Map<K, V> concat(Map<? extends K, ? extends V>... maps) {
		Map<K, V> combinedMap = new HashMap<K, V>();
		for(Map<? extends K, ? extends V> map : maps) {
			combinedMap.putAll(map);
		}
		return combinedMap;
	}


	public static final <K, V> Map<V, K> invert(Map<K, V> source) {
		Map<V, K> dst = new HashMap<>();
		return invert(source, dst);
	}


	public static final <K, V> Map<V, K> invert(Map<K, V> source, Map<V, K> dst) {
		boolean res = tryInvert(source, dst);
		if(!res) {
			throw new IllegalArgumentException("map values are not unique, cannot invert map");
		}
		return dst;
	}


	public static final <K, V> boolean tryInvert(Map<K, V> source, Map<V, K> dst) {
		for(Entry<? extends K, ? extends V> entry : source.entrySet()) {
			if(dst.containsKey(entry.getValue())) {
				return false;
			}
			dst.put(entry.getValue(), entry.getKey());
		}
		return true;
	}

}
