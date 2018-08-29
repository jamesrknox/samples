# Contributing

As a public repository, we encourage and welcome contributions.  

## General

We follow the GitHub flow process, including :

* Repository is public - ie read-only to everyone.
* Collaborators are users with direct push access to the repository
    * Are from TIBCO StreamBase engineering team
    * Are part of github team @streambasesamples
* Any user can submit a pull request
    * The request should mention @streambasesamples
    * One or more collaborators will engage with the aim of accepting the pull request
    * Pull requests can be fore changes to existing samples or proposals for new samples
* All issues are tracked by github issues
    * See https://github.com/plord12/samples/issues
    * Optionally, any related internal jira issues can be referenced
    * Any user can create issues
    * Collaborators are expected to progress issues

## Sample requirements

* Samples consist of a single fragment or single application archive - application archives must contain one or more fragments
* Samples contain documentation in [markdown](https://guides.github.com/features/mastering-markdown/) format conforming to [maven site documentation rules](https://maven.apache.org/guides/mini/guide-site.html), containing at least :
    * Introduction
    * Business logic description, including screen shots
    * For application archives, deployment description
    * Test case description and expected results
    * Links to main files ( such as HOCON configuration and pom.xml )
* Fragments must include junit test case(s)
* Application archives must include integration tests that at least start the application up
* Samples must import into studio with no errors or warnings
* Samples must be built and tests launched from maven - they will be automatically built and tested under a jenkins-based continuous integration system
* Samples cannot contain dependencies not available publicly or part of the StreamBase release
* It must be possible for anyone to setup their own maven repository ( eg follow http://devzone.tibco.com/forums/posts/list/3728.page ), build and execute all tests.
* Samples must go through standard development process including :
  * requirements - reviewed
  * design - reviewed
  * implementation - reviewed
  * testing
