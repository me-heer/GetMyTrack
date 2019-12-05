package getmytrack;

import getmytrack.Authorization;

class GetAccessToken{
    public static void main(String[] args){
        //getting access token
        Authorization.clientCredentials_Sync();
    }
}