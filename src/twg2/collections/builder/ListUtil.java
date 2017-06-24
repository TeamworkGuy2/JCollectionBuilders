package twg2.collections.builder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

/** Utility methods for dealing with {@link List lists}.
 * For example, check if a list contains no duplicate values, get the union between
 * two lists, etc.
 * @author TeamworkGuy2
 * @since 2014-11-9
 */
public final class ListUtil {

	private ListUtil() { throw new AssertionError("cannot instantiate static class ListUtil"); }


	/** Check whether a list contains unique values as defined by {@link Object#equals(Object)}
	 * @param list the list of values to check
	 * @return true if the values in the list form a unique set, false otherwise
	 */
	public static final <E> boolean isUnique(List<E> list) {
		if(list instanceof RandomAccess) {
			int size = list.size();
			for(int i = size - 1; i > -1; i--) {
				E itemI = list.get(i);
				for(int ii = size - 1; ii > -1; ii--) {
					if(i != ii && itemI != null ? itemI.equals(list.get(ii)) : list.get(ii) == null) {
						return false;
					}
				}
			}
			return true;
		}
		else {
			int i = 0;
			for(E itemI : list) {
				int ii = 0;
				for(E itemII : list) {
					if(i != ii && itemI != null ? itemI.equals(itemII) : itemII == null) {
						return false;
					}
					ii++;
				}
				i++;
			}
		}
		return true;
	}


	/** Check whether a sub-list contains unique values as defined by {@link Object#equals(Object)}
	 * @param list the list of values to check
	 * @param off the offset index into {@code list} at which to start comparing values
	 * @param len the number of values to compare, starting at index {@code off} in the {@code list} of values
	 * @return true if the sub-list of values form a unique set, false otherwise
	 */
	public static final <E> boolean isUnique(List<E> list, int off, int len) {
		for(int i = off, size = off + len; i < size; i++) {
			E itemI = list.get(i);
			for(int ii = off, size2 = off + len; ii < size2; ii++) {
				if(i != ii && itemI != null ? itemI.equals(list.get(ii)) : list.get(ii) == null) {
					return false;
				}
			}
		}
		return true;
	}


	/** Create an array of values from a {@link Collection} based on the
	 * class type of the first element in the array
	 * @param coll the collection of values to convert to an array
	 * @return a newly constructed array of all the values from the collection
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(Collection<E> coll) {
		E[] ary = null;
		if(coll instanceof List && coll instanceof RandomAccess) {
			List<E> list = (List<E>)coll;
			int size = coll.size();
			if(size > 0) {
				ary = (E[])Array.newInstance(list.get(0).getClass(), coll.size());
			}
			for(int i = 0; i < size; i++) {
				ary[i] = list.get(i);
			}
		}
		else {
			int i = 0;
			for(E elem : coll) {
				if(i == 0) {
					ary = (E[])Array.newInstance(elem.getClass(), coll.size());
				}
				ary[i] = elem;
				i++;
			}
		}
		return ary;
	}


	/** Transforms a collection of values into a new {@link ArrayList}
	 * @see #map(Iterator, Function, Collection)
	 */
	public static final <E, R> List<R> map(Collection<? extends E> srcColl, Function<E, R> transformer) {
		return map(srcColl, transformer, new ArrayList<>(srcColl.size()));
	}


	/** Transforms an iterator of values into a new {@link ArrayList}
	 * @see #map(Iterator, Function, Collection)
	 */
	public static final <E, R> List<R> map(Iterator<? extends E> srcIter, Function<E, R> transformer) {
		return map(srcIter, transformer, new ArrayList<>());
	}


	/** Map the values from an iterator to a destination collection using a a mapping function
	 * @param srcIter the input collection to convert
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S map(Iterator<? extends E> srcIter, Function<E, R> transformer, S dst) {
		while(srcIter.hasNext()) {
			E val = srcIter.next();
			R obj = transformer.apply(val);
			dst.add(obj);
		}
		return dst;
	}


	public static final <E, G extends BaseStream<? extends E, G>, R> List<R> map(BaseStream<? extends E, ? extends G> stream, Function<E, R> filter) {
		return map(stream.iterator(), filter, new ArrayList<R>());
	}


	public static final <E, G extends BaseStream<? extends E, G>, R, S extends Collection<R>> S map(BaseStream<? extends E, ? extends G> stream, Function<E, R> filter, S dst) {
		return map(stream.iterator(), filter, dst);
	}


	/** Transforms a list of values into a new {@link ArrayList}
	 * @see #map(Iterable, Function, Collection)
	 */
	public static final <E, R> List<R> map(Iterable<? extends E> src, Function<E, R> transformer) {
		return map(src, transformer, new ArrayList<>());
	}


	/** Convert a list using a mapping function and store the resulting values
	 * in a given destination list
	 * @param coll the input collection to convert
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S map(Iterable<? extends E> coll, Function<E, R> transformer, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			List<? extends E> collList = (List<? extends E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				R elem = transformer.apply(collList.get(i));
				dst.add(elem);
			}
		}
		else {
			for(E elem : coll) {
				R obj = transformer.apply(elem);
				dst.add(obj);
			}
		}
		return dst;
	}


	@SafeVarargs
	public static final <E, R> List<R> map(Function<? super E, R> transformer, E... values) {
		return map(transformer, new ArrayList<>(values.length), values);
	}


	/** Map an array of values to a destination list
	 * @param transformer the function to transform the input list values
	 * @param dst the destination collection to store the transformed values in
	 * @param values the array of input values to map to result values
	 * @return the input {@code dst} collection filled with the transformed values
	 */
	@SafeVarargs
	public static final <E, R, S extends Collection<R>> S map(Function<? super E, R> transformer, S dst, E... values) {
		for(E elem : values) {
			R obj = transformer.apply(elem);
			dst.add(obj);
		}
		return dst;
	}


	/** Map an array of values to an {@link ArrayList}
	 * @param values the array of values
	 * @param convert the function to transform the input list values
	 * @return the specified array subset transformed via the {@code convert} function into an {@code ArrayList}
	 */
	public static final <T, R> List<R> map(T[] values, int off, int len, Function<? super T, R> convert) {
		ArrayList<R> dst = new ArrayList<R>();

		for(int i = off, size = off + len; i < size; i++) {
			R res = convert.apply(values[i]);
			dst.add(res);
		}

		return dst;
	}


	/** Filters a list of values into a new {@link ArrayList}
	 * @see #filter(Iterable, Predicate, Collection)
	 */
	public static final <E> List<E> filter(Iterable<? extends E> list, Predicate<E> filter) {
		return filter(list, filter, new ArrayList<>());
	}


	/** Filter a list using a filter function and store the resulting values
	 * in a given destination list
	 * @param coll the input collection to filter
	 * @param filter the function to filter the input list values
	 * @param dst the destination list to store the values in that pass the filter test
	 * @return the input {@code dst} list filled with the filtered values
	 */
	public static final <E, S extends Collection<E>> S filter(Iterable<? extends E> coll, Predicate<E> filter, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			@SuppressWarnings("unchecked")
			List<E> collList = (List<E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				E elem = collList.get(i);
				if(filter.test(elem)) {
					dst.add(elem);
				}
			}
		}
		else {
			for(E elem : coll) {
				if(filter.test(elem)) {
					dst.add(elem);
				}
			}
		}
		return dst;
	}


	public static final <E> List<E> filter(Iterator<? extends E> srcIter, Predicate<E> filter) {
		return filter(srcIter, filter, new ArrayList<E>());
	}


	/** Filter the values from an iterator using a filter function and store the resulting values
	 * in a given destination list
	 * @param srcIter the input iterator to filter
	 * @param filter the function to filter the input list values
	 * @param dst the destination list to store the values in that pass the filter test
	 * @return the input {@code dst} list filled with the filtered values
	 */
	public static final <E, S extends Collection<E>> S filter(Iterator<? extends E> srcIter, Predicate<E> filter, S dst) {
		while(srcIter.hasNext()) {
			E val = srcIter.next();
			if(filter.test(val)) {
				dst.add(val);
			}
		}
		return dst;
	}


	/** Filter a stream using a filter function and store the resulting values
	 * in a given destination list
	 * @see #filter(Iterator, Predicate, Collection)
	 */
	public static final <E, G extends BaseStream<? extends E, G>> List<E> filter(BaseStream<? extends E, ? extends G> stream, Predicate<E> filter) {
		return filter(stream.iterator(), filter, new ArrayList<E>());
	}


	/** Filter a stream using a filter function and store the resulting values
	 * in a given destination list
	 * @see #filter(Iterator, Predicate, Collection)
	 */
	public static final <E, G extends BaseStream<? extends E, G>, S extends Collection<E>> S filter(BaseStream<? extends E, ? extends G> stream, Predicate<E> filter, S dst) {
		return filter(stream.iterator(), filter, dst);
	}


	/** Filers and transforms a list of values into a new {@link ArrayList}
	 * @see #filterMap(Collection, Predicate, Function, Collection)
	 */
	public static final <E, R> List<R> filterMap(Collection<? extends E> list, Predicate<E> filter, Function<E, R> transformer) {
		return filterMap(list, filter, transformer, new ArrayList<>());
	}


	/** Filter and transform a list using a filter and mapping function and store the
	 * resulting values in a given destination list
	 * @param coll the input collection to filter and map
	 * @param filter the function to filter the {@code coll} input values
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S filterMap(Collection<? extends E> coll, Predicate<E> filter, Function<E, R> transformer, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			@SuppressWarnings("unchecked")
			List<E> collList = (List<E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				E elem = collList.get(i);
				if(filter.test(elem)) {
					R obj = transformer.apply(elem);
					dst.add(obj);
				}
			}
		}
		else {
			for(E elem : coll) {
				if(filter.test(elem)) {
					R obj = transformer.apply(elem);
					dst.add(obj);
				}
			}
		}
		return dst;
	}


	public static final <E1, E2, R> List<R> combine(Collection<? extends E1> col1, Collection<? extends E2> col2, BiFunction<E1, E2, R> combiner) {
		return combine(col1, col2, combiner, new ArrayList<R>());
	}


	public static final <E1, E2, R, S extends Collection<R>> S combine(Collection<? extends E1> col1, Collection<? extends E2> col2, BiFunction<E1, E2, R> combiner, S dst) {
		int size1 = col1.size();
		int size2 = col2.size();
		if(size1 != size2) {
			throw new IllegalArgumentException("cannot combine collections with different lengths, collection 1 has " + size1 + " elements, collection 2 has " + size2);
		}

		Iterator<? extends E1> iter1 = col1.iterator();
		Iterator<? extends E2> iter2 = col2.iterator();

		for(int i = 0; i < size1; i++) {
			if(!iter1.hasNext() || !iter2.hasNext()) {
				throw new IllegalArgumentException((!iter1.hasNext() ? "collection 1" : "collection 2") + " had fewer elements (" + i + ") than expected size (" + size1 + ")");
			}
			E1 e1 = iter1.next();
			E2 e2 = iter2.next();
			R res = combiner.apply(e1, e2);
			dst.add(res);
		}
		return dst;
	}


	public static final <E1, E2, R> R[] combineArray(Collection<? extends E1> col1, Collection<? extends E2> col2, BiFunction<E1, E2, R> combiner, R[] dst) {
		int size1 = col1.size();
		int size2 = col2.size();
		if(size1 != size2) {
			throw new IllegalArgumentException("cannot combine collections with different lengths, collection 1 has " + size1 + " elements, collection 2 has " + size2);
		}

		Iterator<? extends E1> iter1 = col1.iterator();
		Iterator<? extends E2> iter2 = col2.iterator();

		for(int i = 0; i < size1; i++) {
			if(!iter1.hasNext() || !iter2.hasNext()) {
				throw new IllegalArgumentException((!iter1.hasNext() ? "collection 1" : "collection 2") + " had fewer elements (" + i + ") than expected size (" + size1 + ")");
			}
			E1 e1 = iter1.next();
			E2 e2 = iter2.next();
			R res = combiner.apply(e1, e2);
			dst[i] = res;
		}
		return dst;
	}

}
