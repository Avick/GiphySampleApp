# GiphySampleApp to show gif files using API's from [giphy.com](http://giphy.com/)
-------
used

- realm for database 

- retrofit for network

- Google location API 

- for selecting location

- [EventBus](https://github.com/greenrobot/EventBus)

- [Glide to show gif image](https://github.com/bumptech/glide)

Activity and Fragments
----
used BaseActivty and BaseFragment to write all the common works for activity and fragment respectively.

**HomeActivity** shows all the gif image related results.

Made a custom popup for search suggestion using SearchSuggestionPopup.

Create EndlessRecyclerOnScrollListener which gives the abtraction for pagination in recyclerview.


Network
----
NetworkDAO handles all the networkcall and network initialization.

GiphyService is the retrofit service.

All the Requests come to NetworkRequestListener from where eventbus post the request and subscriber take the request.
