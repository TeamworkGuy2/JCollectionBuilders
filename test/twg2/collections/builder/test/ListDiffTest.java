package twg2.collections.builder.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.AddedRemoved;
import twg2.collections.builder.ListDiff;
import checks.CheckCollections;

/**
 * @author TeamworkGuy2
 * @since 2016-2-4
 */
public class ListDiffTest {

	@Test
	public void emptyAOrB() {
		AddedRemoved<Integer> diff;
		diff = ListDiff.diff(list(1, 2, 3), list());
		Assert.assertEquals(list(), diff.getAdded());
		Assert.assertEquals(list(1, 2, 3), diff.getRemoved());

		diff = ListDiff.diff(list(), list(1, 2, 3));
		Assert.assertEquals(list(1, 2, 3), diff.getAdded());
		Assert.assertEquals(list(), diff.getRemoved());
	}


	@Test
	public void added() {
		AddedRemoved<Integer> diff;
		diff = ListDiff.diff(list(1, 2), list(3, 4));
		Assert.assertEquals(list(3, 4), diff.getAdded());
	}


	@Test
	public void removed() {
		AddedRemoved<Integer> diff;
		diff = ListDiff.diff(list(1, 2, 3, 4), list(2, 4));
		Assert.assertEquals(list(1, 3), diff.getRemoved());
	}


	@Test
	public void addedAndRemoved() {
		AddedRemoved<Integer> diff;
		diff = ListDiff.diff(list(1, 2, 3), list(2, 4));
		Assert.assertEquals(list(4), diff.getAdded());
		Assert.assertEquals(list(1, 3), diff.getRemoved());
	}


	@SafeVarargs
	private static final <T> List<T> list(T... ts) {
		return Arrays.asList(ts);
	}


	@Test
	public void listDiffTest() {
		List<String> a = Arrays.asList("a", "Bb", "Ccc", "DdD", "eEe");
		List<String> b = Arrays.asList("a", "Ccc", "eEe", "G");
		List<String> expect = Arrays.asList("DdD", "Bb", "G");

		List<String> abDiff = ListDiff.looseDiff(a, b);
		CheckCollections.assertLooseEquals(expect, abDiff);
	}

}
