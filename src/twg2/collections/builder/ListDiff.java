package twg2.collections.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author TeamworkGuy2
 * @since 2016-2-4
 */
public final class ListDiff {

	private ListDiff() { throw new AssertionError("cannot instantiate static class ListDiff"); }


	/**
	 * @param a
	 * @param b
	 * @return an entry, the key is an array of items to add to 'a', the value is an array of items to remove from 'a', applying the add and remove lists to list 'a' convert it to list 'b'
	 */
	public static final <T> AddedRemoved<T> diff(List<T> a, List<T> b) {
		int aLen = a.size();
		int bLen = b.size();
		List<T> added = new ArrayList<>();
		List<T> removed = new ArrayList<>();

		// if either list is empty, the difference is the non-empty list
		if(aLen == 0) {
			added.addAll(b);
			return new AddedRemoved<>(added, removed);
		}
		if(bLen == 0) {
			removed.addAll(a);
			return new AddedRemoved<>(added, removed);
		}

		boolean[] bUsed = new boolean[bLen];
		// keep track of each element in {@code a} that is not in {@code b}
		for(int i = 0; i < aLen; i++) {
			T elemI = a.get(i);
			int matchingIdex2 = -1;
			for(int k = 0; k < bLen; k++) {
				if(bUsed[k] != true && (elemI != null ? elemI.equals(b.get(k)) : b.get(k) == null)) {
					matchingIdex2 = k;
					break;
				}
			}
			if(matchingIdex2 == -1) {
				removed.add(a.get(i));
			}
			else {
				bUsed[matchingIdex2] = true;
			}
		}

		for(int j = 0; j < bLen; j++) {
			if(!bUsed[j]) {
				added.add(b.get(j));
			}
		}

		return new AddedRemoved<>(added, removed);
	}


	/** The symmetric difference between two lists. The values in list {@code e1} and {@code e2} that aren't contained in both lists.
	 * @param e1 the first list
	 * @param e2 the section list
	 * @return a new list containing the values from {@code e1} and {@code e2} that aren't contained in both lists
	 */
	public static final <E> List<E> looseDiff(List<E> e1, List<E> e2) {
		List<E> diff = new ArrayList<>(e1);
		diff.addAll(e2);

		Set<E> commonToBoth = new HashSet<>(e1);
		commonToBoth.retainAll(e2);

		diff.removeAll(commonToBoth);

		return diff;
	}

}
