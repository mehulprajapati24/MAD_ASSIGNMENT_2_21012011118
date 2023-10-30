# MAD_ASSIGNMENT_2_21012011118


App Name: NewsHub


Description: The NewsHub is a mobile app that provides users with up-to-date news articles from various reliable sources. Stay informed about current events, top stories, and trending topics in an easy-to-use and intuitive interface.


Features:
Browse a wide range of news categories, including General, Technology, Entertainment, Sports, and more.
View detailed articles with images, headlines, and summaries.
Search for specific news topics or keywords.
Save news articles for later viewing, creating a personalized reading list.
Remove saved articles from your list as needed.
Stay connected with the world and never miss out on important news updates with the News Application.


Installation:
To use this project in your Android application, follow these steps:
Step 1. Add the JitPack repository to your root build.gradle file.
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
Step 2 : Download via Gradle:
implementation 'com.github.KwabenBerko:News-API-Java:1.0.2'
implementation 'com.squareup.picasso:picasso:2.8'


Permissions:
To ensure proper functionality, this application requires the following permission:
INTERNET: This permission is necessary for the app to access the internet and retrieve data from external sources.
