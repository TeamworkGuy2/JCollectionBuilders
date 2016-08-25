# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.1.1](N/A) - 2016-08-25
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
