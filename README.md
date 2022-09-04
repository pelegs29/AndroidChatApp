<h1 align="center">
  <img src="https://user-images.githubusercontent.com/80215741/165474084-8f6d693c-df78-4b55-bd0a-1759f91b4f38.png" width="12%" alt="logo"/>
  <br/>
  Welcome to our Android Chat App
  <img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="5%" alt="waveEmoji"/>
  <img src="https://user-images.githubusercontent.com/80215741/175117347-72c1240e-905d-4074-9c0a-d16803501ff5.gif" width="8%" alt="waveEmoji"/>
</h1>

<h2 align="center">
   This is a responsive android chat app built with java using android studio. <br/>
.net core RESTful api server & mariaDB fused with firecore notification service for realtime chat experience.

</h2>

<br/>

# Table of Contents

* [Features](#features)
* [Upcoming Features](#upcoming)
* [Installation](#install)
* [Run](#run)
* [Configuration](#config)
* [DB Quick Start](#db)
* [Credits](#credit)

<br/>

# :heavy_check_mark: Features <a name="features"/>

1. **User Management** - sign-in and sign-up :
    - intuitive and responsive feedback forms
    - picture upload on sign up.

<br/>

2. **Contact List** :
    - chats organized by recent conversations
    - see last messages in a glance
    - easily start new conversation with another user (even pick his nickname !)
    - easily access setting panel to change you server url

<br/>

3. **Rich Conversation Page** :
    - distinguishable chat bubble colors
    - time and date stamp for each message
    - chat input bar will raise with keyboard

<br/>

4. **Realtime chat** :
    - send messages to any other user using the app
    - cross server communication in real time
    - chat with users from different servers and different devices





<img  alt="Screen Shot 2022-09-04 at 19 55 07" src="https://user-images.githubusercontent.com/92450447/188325196-78da100f-05cf-4ae2-b3ff-60fec8f3e824.png"  width="65%" >
<br/>
<img  alt="Screen Shot 2022-09-04 at 19 55 21" src="https://user-images.githubusercontent.com/92450447/188325262-c2307017-9453-45ab-be44-e0c0dcc455c1.png"  width="65%" >
<br/>
<br/>

# :construction_worker: Upcoming Features <a name="upcoming"/>

1. **Emoji picker**
2. **Powerful Contact list** - edit nickname, profile picture, status, etc.
3. **Rich Content** - send image, video, voice message and more !

<br/>

# :wrench: Installation <a name="install"/>

our chat app is comprised of three parts :

1. **Frontend** :
    - React web-app
    - Android app
2. **Backend** :
    - .net core RESTful api server
3. **Database** :
    - mariaDB

First thing will be to setup the mariaDB database. \
head over to the [GitHub repository](https://github.com/pelegs29/chatServerAndroid) and download all of the git
files in this repository, and follow these steps to install the mariaDB database.

1. Open the android chat server project in the IDE of your choice : either Rider or Visual Studio.
2. Install the mariaDB from the main mariaDB page : https://mariadb.com/download/
    - at the installation process you will be asked to choose username and password for the mariaDB user.
    - we recommend you to use the default username 'root', and write down the password we will use it later.
3. Head over to "repoExtractedFolder/Repository/ChatDbContext.cs" file and edit the connection string to match your
   inserted username and password.
4. Create the database:
    1. Visual Studio - Open the Packet Manager Console and run the following commands :
       ```sh
       add-migration InitialMigration
       ```
       ```sh
       update-database
       ```
    2. Rider - install the Entity Framework Core UI plugin from the Rider Marketplace or by following this
       link : [Plugin](https://marketplace.visualstudio.com/items?itemName=Microsoft.EntityFrameworkCore.Tools) and then
       from the Tools menu, select the Entity Framework Core Tools menu item and then select the Add-Migration command.
       then select the Update-Database command.
5. _OPTIONAL_ : you could install now an administration tool for MySQL such as HeidiSQL, and log in with the credentials
   you picked at the mariaDB installation process.

<br/>
in this section, we will show you how to install **Frontend** and **Backend**.
First,
we will notice that we have 6 folders in our downloaded repo files, we will focus on two of the folders:

##### chatServerAPI and chatServerReact.

\
as you can implicate the API folder will host the files necessary to run the api server and the React folder the chat
server app itself.

1. Prepare the api server : \
   in order to run the .net core RESTful api server we need an IDE of choice : we recommend **Rider** by jetbrain OR **
   visual studio** by microsoft. \
   install the IDE of your choice and open existing project by locating the "chatServer.sln" file in the main repo
   folder.

<br/>

2. Prepare the react app : \
   here you can either use an IDE such as: **VSCode** or **Rider/Webstorm** by jetbrain, \
   you will need to set up the chatAppReact as your main directory. \
   after that you can continue to the linux guide below.

<br/>
<br/>

###### Linux Guide For installing your react app :

1. First step would be to make sure you linux system and packages are updated :

```sh
sudo apt -y update && sudo apt -y upgrade
```

<br/>

2. Second step will be to install Node.js bundled with NPM, we will install them using the curl library provider
   which is recommended by the node.js devs, lastly we will install the latest stable versions by this time of writing
   which are node.js version 16 and npm version 8 by running these commands :

```sh
sudo apt install -y curl
curl -fsSL https://deb.nodesource.com/setup_16.x | sudo -E bash -
sudo apt-get install -y nodejs
```

<br/>

3. Third step will be to make sure you are able to compile native addons from npm, for\
   such support you will need to install the development tools :

```sh
sudo apt install -y build-essential
```

<br/>

4. The forth and last step will be to download the project files from git to your project directory of choice,\
   now , we will install the npm addons needed for this project :\
   \
   **Notice : make sure you run this command from the project directory (repoExtractedFolder/chatAppReact/) !**

```sh
npm install
```

<br/>

now our **backend** api server is ready to run and our **frontend** react app is ready to run, \
in order to run the android app you will need to install the android sdk and the android emulator. \
the easy way to install them both will be to download and install android studio. \
after that, download the project files from this repository and open this project in android studio.


<br/>

# :arrow_forward: Run <a name="run"/>

**Running the api server :**

running from the IDE of your choice is as simple as running the Run configuration that automatically was created when
you opened the project.

<br/>

**Running the react app :**

**Notice : make sure you run this command from the project directory (repoExtractedFolder/chatAppReact/) !**

1. First we will build the app for production to the build folder,
   It correctly bundles React in production mode and optimizes the build for the best performance.
   The build is minified and the filenames include the hashes.
   after this command the app is ready to be deployed!

```sh
npm run build
```

<br/>

2. Second, they only thing left to do is run the app,
   this command will run the app in the development mode and the app will open automatically in your default browser,
   if it doesn't Open http://localhost:3000 in your browser.

```sh
npm start
```

<br/>

**Running the android app :**

simply press the play button in the top right corner of the android studio window.

<br/>

# :gear: Configuration <a name="config"/>

in fact you can choose you own port for your api server or react project, we will show you how in this guide :

Setting up custom port for you api server :

1. to change the port of your api server head over to <br/> "../repoExtractedFolder/chatServerAPI/Program.cs" and
   under "profiles" your will see
    ```sh
    "applicationUrl": "http://localhost:XXXX"
    ```
   from here you can pick any 4 digit port for you local server to run on.
   in order for the server to approve connection from another servers or users head over to "
   ../repoExtractedFolder/chatServerAPI/Program.cs" and in line 72 you will see the "AddCors" function,
   you will see 2 polices we have added, one for servers and the other for apps,
    - to add new server simply add the line :
        ```sh
        builder.WithOrigins("http://localhost:XXXX").AllowAnyMethod().AllowAnyHeader().AllowCredentials();
        ```
      to the "cors_policy".

    <br/>

    - to add new app simply add the line :
        ```sh
        .WithOrigins("http://localhost:XXXX")
        ```
      to the "ClientPermission" policy.

      <br/>
    - we have already added for you 2 servers on the ports 5125 and 5126 and 4 app ports on 3000 - 3003.

<br/>

2. Setting up custom port for you react app :

   head over to "../repoExtractedFolder/chatAppReact/package.json" and look for scripts, there you will see this line :
    ```sh
    "start": "set PORT=XXXX && react-scripts start",
    ```
   simply replace XXXX to any local port you set up your react app to run on,
   dont forget to choose a port that exist in the "ClientPermission" policy in the server from step 1.

<br/>

3. Set up react app to work with your server :

   head over to  "../repoExtractedFolder/chatAppReact/src/App.js" and you will see right after the import a variable
   named "server",
    ```sh
    let server = "http://localhost:XXXX";
    ```
   simply replace XXXX to the local port you set up your api server to in step 1.

<br/>

3. Set up android app to work with your server :

   as you probably noticed from the android chat app in the contacts list you cant access the setting page from the left
   bottom of the app, from there you can change the server url easily.

# :memo: DB Quick Start <a name="db"/>

for quick start with the database we have created an exported database called "ChatAppDB" in the "
repoExtractedFolder/Repository/Database" folder. \
we will guide you how to load this database into your sqlite database easily using HeidiSQL.

1. firstly you will need to download the HeidiSQL application if you didnt already.
2. then log in using the credentials you picked at the mariaDB installation.
3. then open the database in HeidiSQL and at the left side of the screen you will see a list of databases, locate the
   database
   called "ChatAppDB" and click on it.
4. open the Query tab (blue play button :arrow_forward: ) and then right click on the background, select "Load SQL
   file..." .
5. locate the file "ChatAppDB.sql" and click on it.
6. then click on the "Execute" button.
7. enjoy your database!

<br/>

# :trophy: Credits <a name="credit"/>

> Nadav Yakobovich

> Peleg Shlomo

<br/>
