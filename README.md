# BooksAPIApp

This is a simple app which will allow users to search the books/articles available in Google Books API.
App simply queries the Google Books API for volumes having the keywords entered in search bar and display upto 40 results.
40 is the maximum number of results currently supported by the API.

The search results are then rendered in a gridview which consists of an image and a title of the book. 
Tapping on any of the books from the list opens a details page where some of the metadata related to the books is rendered.

Google API endpoint https://www.googleapis.com/books/v1/volumes

Libraries used

AppCompat Libs
Gson
Picasso
