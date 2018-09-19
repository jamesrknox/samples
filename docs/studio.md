# Using in TIBCO StreamBase Studio&trade;

* [Recommended plugins](#recommended-plugins)
* [Options to import samples to studio](#options-to-import-samples-into-tibco-streambase-studio-trade)

## Recommended plugins

* egit 5.1

Egit 5.1 (with auto-import) will soon be available in studio.  Pending this, use :

    * Help -> Install new software
    * Set **Work with** to http://download.eclipse.org/egit/updates-stable-nightly/
    * Tick at least **Git integration for Eclipse** and **Git integration for Eclipse - auto-import**

* markdown editor

Sample contributors may want to install a markdown editor :

    * Help -> Install new software
    * Set **Work with** to https://www.winterwell.com/software/updatesite/
    * Tick select all
    

## Options to import samples into TIBCO StreamBase Studio&trade;
 
* Import samples using egit 5.1 ( not currently in studio builds )

    * Import -> Projects from Git ( with smart import )

    ![egit51 smart import](egit51-smartimport.png)

    * Clone URI
    * Set **URI** to https://github.com/plord12/samples.git

    ![egit51 select](egit51-select.png)

    * Choose what sample(s) to import

    ![egit51 import](egit51-import.png)

* Import samples using egit 5.0 ( currently in studio builds )

    * Window -> Show view -> Other -> Git -> Git Repositories
    * Click **Clone a Git repository**

    ![egit50 import](egit50-clone.png)

    * Set **URI** to https://github.com/plord12/samples.git

    ![egit50 select](egit50-select.png)

    * Next -> Next -> Finish
    * Expand Working Tree and select sample and Import Projects

    ![egit50 import projects](egit50-importprojects.png)

    * Finish

    ![egit50 import](egit50-import.png)

* Import samples using m2e-git ( not currently in studio builds )

    * Import -> Maven -> Check out Maven Projects from SCM
    * Set **SCM URL** to git and https://github.com/plord12/samples.git
    * Untick **Check out All Projects**

    ![m2e-git select](m2e-git-select.png)

    * Select Next then Finish
    * Choose what sample(s) to import

    ![m2e-git import](m2e-git-import.png)
