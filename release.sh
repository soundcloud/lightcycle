
set -e
set -x

VERSION=$1
SNAPSHOT=$2

updateToVersion() {
        version=$1
        sed -i"" "s/VERSION_NAME=.*/VERSION_NAME=$version/" gradle.properties
        git add gradle.properties
        git diff --quiet --exit-code --cached || git commit -m "Bump version to $version"
}

release() {
        updateToVersion "$VERSION"
        git tag $VERSION
        git push origin "$VERSION"
        git push origin master
        ./gradlew uploadArchives
}

snapshot() {
        updateToVersion "$SNAPSHOT"
        git push origin master
        ./gradlew uploadArchives
}

git checkout master && git pull
release
snapshot