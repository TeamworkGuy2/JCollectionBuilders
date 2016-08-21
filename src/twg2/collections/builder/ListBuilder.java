package twg2.collections.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-4-18
 */
public class ListBuilder {


	@SafeVarargs
	public static <E> List<E> of(E... elements) {
		return immutable(elements);
	}
	

	public static <E> List<E> of(Iterable<E> iter) {
		return immutable(iter);
	}


	public static <E> List<E> of(Iterator<E> iter) {
		return immutable(iter);
	}


	@SafeVarargs
	public static <E> List<E> immutable(E... elements) {
		return Collections.unmodifiableList(mutable(elements));
	}
	

	public static <E> List<E> immutable(Iterable<E> iter) {
		return Collections.unmodifiableList(mutable(iter));
	}


	public static <E> List<E> immutable(Iterator<E> iter) {
		return Collections.unmodifiableList(mutable(iter));
	}


	@SafeVarargs
	public static <E> List<E> mutable(E... elements) {
		ArrayList<E> list = new ArrayList<>(elements.length);
		for(E elem : elements) {
			list.add(elem);
		}
		return list;
	}


	public static <E> List<E> mutable(Iterable<E> iter) {
		ArrayList<E> list = new ArrayList<>();
		for(E elem : iter) {
			list.add(elem);
		}
		return list;
	}


	public static <E> List<E> mutable(Iterator<E> iter) {
		ArrayList<E> list = new ArrayList<>();
		while(iter.hasNext()) {
			E elem = iter.next();
			list.add(elem);
		}
		return list;
	}


	public static <T> List<T> copyDeep(Collection<T> lists) {
		List<T> dst = new ArrayList<>();

		copyDeepTo(lists, dst);

		return dst;
	}


	public static <T> void copyDeepTo(Collection<T> lists, Collection<T> dst) {
		for(T elem : lists) {
			if(elem instanceof Collection) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				T subList = (T)copyDeep((List)elem);
				dst.add(subList);
			}
			else {
				dst.add(elem);
			}
		}
	}

}
