# Contributing

As a public repository, we encourage and welcome contributions.  

## General

We follow the GitHub flow process, including :

* Repository is public - ie read-only to everyone.
* Collaborators are users with direct push access to the repository
    * Are from TIBCO StreamBase engineering team
    * Are part of github team @streambasesamples
    * Commit messages should be clear and concise, describing the change well and referencing jira or github issue number
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
* Samples must be built and tests launched from maven
  * they will be automatically built and tested under a jenkins-based continuous integration system
  * Maven site docs will be published to github pages
* Ideally samples shouldn't reference 3rd party dependencies not available publicly or part of the StreamBase release. 
    This allows samples to work without additional steps for both customers and automated builds.  However, when this isn't possible, the following applies : 
    * If the dependency is available on a vendor maintained maven repository, instructions are provided in that sample to use that repository.
    * If the dependency is only available with a manual download, instructions are provided in the sample to to manually download the dependency, install into 
    the local maven repository and (optionally) deploy to a shared repository.
    * Internally, we do the same but deploy (or mirror) the dependency to a shared 3rd party repository. This repository is included in the sample builds.
    * Care must be taken to keep the metadata intact ( for example maintain copyright and license information ) so that the maven site info reports are correct.
* It must be possible for anyone to setup their own maven repository ( eg follow http://devzone.tibco.com/forums/posts/list/3728.page ), build and execute all tests.
* Samples must go through standard development process including :
  * requirements - reviewed
  * design - reviewed
  * implementation - reviewed
  * testing

## Empty directories

Git doesn't store empty directories, so such directories that need to be part of the sample should include an empty .gitignore file.

## Index

Jenkins will re-generate README.md files based on the pom.xml metadata.