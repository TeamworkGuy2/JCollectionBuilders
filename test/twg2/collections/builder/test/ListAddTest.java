package twg2.collections.builder.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.builder.AddCondition;
import twg2.collections.builder.ListAdd;
import twg2.collections.builder.ListUtil;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2014-12-16
 */
public class ListAddTest {

	@Test
	public void listAddTest() {
		{
			ArrayList<String> strs = new ArrayList<>();

			// add non-null items
			ListAdd.addArrayToList(new String[] { "a", "ab", "abc", null }, strs, AddCondition.NO_NULL);
			Assert.assertEquals("invalid number of strings", strs.size(), 3);

			// add a subset of an array, also throw errors if null
			CheckTask.assertException(() -> {
				ListAdd.addArrayToList(new String[] { "b", null, "c" }, 1, 1, strs, true, false, false, true);
			});

			// add items with a duplicate at the end and throw errors if contains, and catch error
			CheckTask.assertException(() -> {
				ListAdd.addListToList(Arrays.asList("c", "d", "e", "a"), strs, AddCondition.ERROR_CONTAINS);
			});
			Assert.assertTrue("lists not equal", Arrays.asList("a", "ab", "abc", "c", "d", "e").equals(strs));

			// add items with duplicates, but don't allow duplicates and check for duplicates
			ListAdd.addCollectionToList(new HashSet<>(Arrays.asList("e", "f")), strs, AddCondition.NO_NULL_OR_CONTAINS);
			Assert.assertTrue("list should be unique", ListUtil.isUnique(strs));
			Assert.assertTrue("list should be unique", ListUtil.isUnique(strs, 0, strs.size()));
			Assert.assertTrue("collection should be unique", ListUtil.isUnique(new LinkedList<>(strs)));

			// add a duplicate item and check for duplicates
			ListAdd.addCollectionToList(new HashSet<>(Arrays.asList("f")), strs, AddCondition.ADD_ALL);
			Assert.assertTrue("list should have duplicate", !ListUtil.isUnique(strs));
			Assert.assertTrue("list should have duplicate", !ListUtil.isUnique(strs, 0, strs.size()));
			Assert.assertTrue("collection should have duplicate", !ListUtil.isUnique(new LinkedList<>(strs)));
		}

		{
			ArrayList<Integer> list = new ArrayList<>();

			// add items with nulls, but no duplicates
			ListAdd.addListToList(new LinkedList<>(Arrays.asList(1, 2, null)), list, AddCondition.NO_CONTAINS);
			Assert.assertEquals(Arrays.asList(1, 2, null), list);
		}
	}

}
