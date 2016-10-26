# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.2.0](N/A) - 2016-10-26
#### Added
* Added GroupBy class with groupBy() and groupByList() methods
* Added ListAdd.copy() with AddCondition parameter to conditionally copy Iterable items to a List

#### Changed
* Renamed ListAdd add*ToList() methods (such as addArrayToList()) to addToList() overloads
* Added and updated unit tests

#### Removed
* Removed ListUtil copyOfKeys() and copyOfValues() - in favor of ListAdd.copy()


--------
###[0.1.1](https://github.com/TeamworkGuy2/JCollectionBuilders/commit/ba023070fa9e73f73e61b012a6815eb27237d203) - 2016-08-25
#### Added
Moved jstreamish StreamMap class methods to this project
* ListUtil.map(T[], int, int, Function) to map sub-arrays
* MapUtil mapRequireUnique() and mapCheckNewKeyUniqueness() to convert a map's keys and values to a new map while ensuring uniqueness of the new keys

#### Changed
* Expanded the ListUtil.map(Function<E, R>, E...) first parameter signature to Function<? super E, R>


--------
###[0.1.0](https://github.com/TeamworkGuy2/JCollectionBuilders/commit/0a48938c4ca2ed7d6b473642fd5da9c56a94b3e5) - 2016-08-21
#### Added
* Extracted twg2.collections.builder package from JCollectionUtil project into this new JCollectionBuilders project
