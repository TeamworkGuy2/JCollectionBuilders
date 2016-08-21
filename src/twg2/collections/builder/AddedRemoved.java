package twg2.collections.builder;

import java.util.AbstractMap;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2016-2-4
 * @param <E> the type of elements 
 */
@SuppressWarnings("serial")
public class AddedRemoved<E> extends AbstractMap.SimpleImmutableEntry<List<E>, List<E>> {

	public AddedRemoved(List<E> a, List<E> b) {
		super(a, b);
	}


	public List<E> getAdded() {
		return super.getKey();
	}


	public List<E> getRemoved() {
		return super.getValue();
	}

}