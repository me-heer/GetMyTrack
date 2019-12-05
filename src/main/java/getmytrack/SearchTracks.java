package getmytrack;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;


import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


class SearchTracks{
    private static final String accessToken = Authorization.spotifyApi.getAccessToken();
    private static final String q = "Abba";
  
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(q)
  //          .market(CountryCode.SE)
  //          .limit(10)
  //          .offset(0)
            .build();
  
    public static void searchTracks_Sync() {
      try {
        final Paging<Track> trackPaging = searchTracksRequest.execute();
  
        System.out.println("Total: " + trackPaging.getTotal());
      } catch (IOException | SpotifyWebApiException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  
    public static void searchTracks_Async() {
      try {
        final CompletableFuture<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();
  
        // Thread free to do other tasks...
  
        // Example Only. Never block in production code.
        final Paging<Track> trackPaging = pagingFuture.join();
  
        System.out.println("Total: " + trackPaging.getTotal());
      } catch (CompletionException e) {
        System.out.println("Error: " + e.getCause().getMessage());
      } catch (CancellationException e) {
        System.out.println("Async operation cancelled.");
      }
    }
  }