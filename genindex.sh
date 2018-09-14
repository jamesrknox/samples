#!/bin/sh

index="README.md"

here=$(pwd)
for dir in $(find . -type d)
do
	if [ -f ${dir}/pom.xml ]
	then
		cd ${dir}
		echo "In ${dir}"
		artifactId=$(/Users/plord/workspace/3rdparty/generic/maven/3.5.4/bin/mvn -N -Dexec.executable='echo' -Dexec.args='${project.artifactId}' exec:exec -q)
		name=$(/Users/plord/workspace/3rdparty/generic/maven/3.5.4/bin/mvn -N -Dexec.executable='echo' -Dexec.args='${project.name}' exec:exec -q)
		description=$(/Users/plord/workspace/3rdparty/generic/maven/3.5.4/bin/mvn -N -Dexec.executable='echo' -Dexec.args='${project.description}' exec:exec -q)

		echo "# ${name}" > ${index}
		echo "" >> ${index}
		echo "${description}" >> ${index}
		echo "" >> ${index}

		if [ -d docs ]
		then
			echo "Overview:" >> ${index}
			echo "" >> ${index}
			echo "* [Using in Studio](docs/studio.md)" >> ${index}
			echo "* [Contributing](docs/contributing.md)" >> ${index}
			echo "" >> ${index}
			echo "Samples:" >> ${index}
			echo "" >> ${index}
		fi

		if [ -f ../${index} ]
		then
			echo "* [${description}]($(basename ${dir}))" >> ../${index}
		fi
	
		if [ -f src/site/markdown/index.md ]
		then
			echo "* [${artifactId}](src/site/markdown/index.md)" >> ${index}
			echo "* [${artifactId} site documentation](https://plord12.github.io/samples/${dir})" >> ${index}
		fi

		cd ${here}
	fi
done


