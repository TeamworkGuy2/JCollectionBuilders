package twg2.collections.builder.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import checks.CheckTask;
import twg2.collections.builder.ListBuilder;

/**
 * @author TeamworkGuy2
 * @since 2015-7-12
 */
public class ListBuilderTest {

	@Test
	public void copyDeepTest() {
		List<List<Object>> deepList = list(
				list("A", "B", "C"),
				list("str1"),
				list(list("01", "02"), "3"),
				list(list(list("<", ">"), list("=")), "3")
			);

		List<List<Object>> copiedList = ListBuilder.copyDeep(deepList);
		Assert.assertEquals(deepList, copiedList);
	}


	@Test
	public void createListTest() {
		Assert.assertEquals(list(1, 2, 3), ListBuilder.of(1, 2, 3));
		Assert.assertEquals(list(1, 2, 3), ListBuilder.of(list(1, 2, 3)));
		Assert.assertEquals(list(1, 2, 3), ListBuilder.of(list(1, 2, 3).iterator()));
	}


	@Test
	public void immutableTest() {
		CheckTask.assertException(() -> ListBuilder.of(1, 2).add(3));
		CheckTask.assertException(() -> ListBuilder.of(list(1, 2)).add(3));
		CheckTask.assertException(() -> ListBuilder.of(list(1, 2).iterator()).add(3));
	}


	@Test
	public void mutableTest() {
		List<Integer> list;
		list = ListBuilder.mutable(1, 2);
		list.add(3);
		Assert.assertEquals(list(1, 2, 3), list);
	}


	@SafeVarargs
	private static final <T> List<T> list(T... ts) {
		return Arrays.asList(ts);
	}

}
