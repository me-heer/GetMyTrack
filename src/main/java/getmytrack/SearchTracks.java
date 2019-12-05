package getmytrack;


import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;



import java.io.IOException;
import java.util.Scanner;

class SearchTracks{
    private static final String accessToken = Authorization.spotifyApi.getAccessToken();
    private static final String q = getQueryString();
  
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();

    public static String getQueryString(){
      System.out.println("Enter search query: ");
      Scanner input = new Scanner(System.in);
      return input.nextLine();
    }
  
 
    private static final String type = ModelObjectType.TRACK.getType();

    private static final SearchItemRequest searchItemRequest = spotifyApi.searchItem(q, type)
//          .market(CountryCode.SE)
//          .limit(10)
//          .offset(0)
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
                  
  public static void searchItem_Sync() {
    try {
      SearchItemRequest.Builder mySearch = new SearchItemRequest.Builder(accessToken);
      mySearch.q(q).type("artist,track");
      //final SearchResult searchResult = searchItemRequest.execute();
      SearchItemRequest mySIR = mySearch.build();
      final SearchResult searchResult = mySIR.execute();
      Paging<Track> tracks = searchResult.getTracks();
      Track[] myTracks = tracks.getItems();
      for(int i = 0; i < myTracks.length; i++)
      {
        ArtistSimplified[] myArtists = myTracks[i].getArtists();
        for(int j = 0; j < myArtists.length; j++)
        {
          System.out.print(myArtists[j].getName() + ", ");  
        }
        System.out.println("\b\b - " + myTracks[i].getName());
      }
      System.out.println("Total tracks: " + searchResult.getTracks().getTotal());
    } catch (IOException | SpotifyWebApiException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  }