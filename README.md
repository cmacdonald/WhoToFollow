# WhoToFollow In Context
Recommending Twitter users to follow for information about events.

## Pre-requisites

1. Install Redis. On a Mac with brew, its simply (which also sets it running):
```
brew install redis
brew services redis start
```

2. Install Node.js and sbt. Again, we had a Mac, so used brew.

```
brew install node
brew install sbt
```

3. Install tsd and gulp into node, globally

```
npm install tsd -g
npm install gulp-cli -g
```

4. Download and install the Maven build of Terrier from http://terrier.org. We assume you have Maven installed.

```
tar -xvf terrier-core-4.1.tar.gz
cd terrier-core-4.1
mvn -DskipTests install
```

## Configuration

1.
Head over to https://dev.twitter.com, and create a new Twitter app. You need to get an access token, an access token secret, consumer key, and consumer key secret. Set these in the environment variables:

```
export WTF_TWITTER_CONSUMER_KEY=
export WTF_TWITTER_CONSUMER_SECRET=
export WTF_TWITTER_ACCESS_TOKEN=
export WTF_TWITTER_ACCESS_TOKEN_SECRET=
```

2. If not operating over the live Twitter API, you need to point WTF at a file of tweets to process. Alter `stream.sourcefile.path` in `conf/application.conf`.

## Build instructions

The Scala portion of this project can be built using [sbt](http://www.scala-sbt.org).
The build tool used for the front-end is [Gulp](http://gulpjs.com).

 1. `git clone` this repository.
 2. `cd WhoToFollow/app/assets/typescript` - Go the the front-end project root.
 3. `npm install` - Install front-end dependencies.
 4. `tsd install` - Fetch TypeScript definition files.
 5. `gulp less` - Compile and bundle the LESS files.
 6. `gulp build` - Transpile and concatenate TypeScript.
 7. `cd ../../../` - Return to the project root
 8. `activator run` - Get server dependencies, compile the backend code and run the server. Auto-reloading is enabled.
