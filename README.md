# Twitter-Wrapper
TwitterWrapper is an application that delivers e-mails with Tweets (from Twitter.com) based on a request received by mail.

The application uses Twitter4j and JavaMail to retrieve Tweets and send them by mail.

To build the application:
- Maven
- Java 1.7

The application is configured to use a Gmail account, you can use it with command line arguments (username, password, oAuthConsumerKey, oAuthConsumerSecret, oAuthAccesToken and oAuthAcessTokenSecret). You need also to generate your access tokens for your Twitter account and replace them in the code as well. (You can find it at http://apps.twitter.com)
