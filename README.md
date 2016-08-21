JCollectionBuilders
==============
version: 0.1.0

Build, filter, and map methods for lists, collections, maps, iterators, and streams. 
* ListAdd - add items to a collection with strict constraints on what can be added (duplicate values allowed or not, null allowed or not, etc.)
* ListDiff - compare 2 lists and return symmetric difference or an exact list of elements each list contains that the other does not
* Helper methods throughout the List and Map Util and Builder classes such as isUnique(List) and tryInvert(Map)

Take a look at the twg2.collections.builder.tests package for examples of how the APIs can be used.
