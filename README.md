# Android Template project

A boilerplate template to be used as reference or to kickstart a project quickly. It should be updated frequently.

## Usage

To be written...

## Jack and java 8

Currently Jack does not support databinding and realm. So retro lambda will be used instead until Jack does support these libraries.

## Annotation Processing

Using the built in version of the gradle plugin 2.2 did not seem to generate dagger classes. For now apt is used.

## TODO

Initial setup to make for the following

* Product flavors/builds ( Stage, test?, mock )
* Setup using mvp for testing purposes
* Dependency version separate

Testing everything

* Unit testing
* UI tests

## Changelog

### 28 October
* Added samples making it a working project

### 14 October 2016
* Added RxJava example with Retrofit2 in the GithubService
* Added Realm implementation

### 11 October 2016

* Added dagger2 setup/example
* Added dagger2, RxJava, Realm dependencies to gradle, Retrofit with GSON, Picasso
* Enabled Databinding


### 10 October 2016
Initialised project